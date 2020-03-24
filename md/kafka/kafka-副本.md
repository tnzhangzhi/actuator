一.kafka启动
1.先启动zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties
2.启动kafka
bin/kafka-server-start.sh config/server.properties

##副本
提供数据冗余以实现高可用高持久

1.在kafka中，副本分成两类，领导者与追随者，每个分区在创建时，都会选出一个领导者副本，其余的成为追随者副本
2.在kafka中，追随者副本不对外提供服务。所有的读写请求都必须发往领导者副本所在的broker处理，追随者的任务就是异步从领导者拉取消息写入日志实现同步
3.当领导者副本所在的broker 宕机时，kafka依托于zookeeper提供的监控功能实时感知，并立即发起一轮选举，从追随者中选举一个领导者，当原领导者重启恢复过来只能作为追随者加入到集群中来

这种副本机制优势：
1.当生产者向kafka成功写入消息后，消费者马上就能读取到刚生产的消息。如果副本提供读，刚才生产的消息异步复制到副本需要时间，消费者可能无法读取到最新消息

### In-sync replicas （ISR）
kafka 引入ISR副本集合，ISR 中的副本就是与leader同步的副本，相反，不在ISR中的副本认为是不同步的，leader天然就在ISR中

Kafka判断Follower是否与Leader同步的标准就是Broker端参数`replica.lag.time.max.ms`，这个参数表示follow副本落后leader副本的最长时间间隔
默认是10秒。因此follow落后leader10s以内，就认为是同步的，及时follow的消息明显少于leader。
如果follow与leader不同步就会踢出ISR，如果追上又会重新加入ISR

### Unclean 领导者选举
ISR 可能出现为空的现象，Kafka 需要重新选举一个新的 Leader。此时如果选择非同步副本作为新 Leader，就可能出现数据的丢失。Broker 端参数 `unclean.leader.election.enable` 控制是否允许 Unclean 领导者选举
开启 Unclean 领导者选举可能会造成数据丢失，但好处是，它使得分区 Leader 副本一直存在，不至于停止对外提供服务，因此提升了高可用性。反之，禁止 Unclean 领导者选举的好处在于维护了数据的一致性，避免了消息丢失，但牺牲了高可用性
建议你不要开启 Unclean 领导者选举

##请求处理
###处理流程
1.Acceptor线程采用轮训的方式将入站请求公平的发到所有网络线程中
2.网络线程池，Broker端参数`num.network.threads` 用于调整网络线程池的线程数。默认值是 3，表示每台 Broker 启动时会创建 3 个网络线程处理客户端发送的请求
3.网络线程拿到请求后，将其放入到一个共享请求队列中
4.IO线程池，负责从共享请求队列中取出请求并处理。如果是 PRODUCE 生产请求，则将消息写入到底层的磁盘日志中；如果是 FETCH 请求，则从磁盘或页缓存中读取消息。Broker 端参数 `num.io.threads` 控制 IO 线程池中的线程数。默认值是 8，表示每台 Broker 启动后自动创建 8 个 IO 线程处理请求。如果机器上 CPU 资源非常充裕，可以调大该参数，允许更多的并发请求被同时处理
5. IO 线程处理完请求后，将生成的响应发送到响应队列中，由对应的网络线程负责将响应返还给客户端

### Purgatory 组件
用来缓存延时请求，即那些一时未满足条件不能立刻处理的请求。比如设置了 acks=all 的 PRODUCE 请求，该请求就必须等待 ISR 中所有副本都接收了消息后才能返回，此时处理该请求的 IO 线程就必须等待其他 Broker 的写入结果。当请求不能立刻处理时，它就会暂存在 Purgatory 中。稍后一旦满足了完成条件，IO 线程会继续处理该请求，并将 Response 放入对应网络线程的响应队列中

### 请求类型
在 Kafka 内部，除了客户端发送的 PRODUCE 请求和 FETCH 请求之外，还有很多执行其他操作的请求类型，比如负责更新 Leader 副本、Follower 副本以及 ISR 集合的 LeaderAndIsr 请求，负责勒令副本下线的 StopReplica 请求等。与 PRODUCE 和 FETCH 请求相比，这些请求有个明显的不同：它们不是数据类的请求，而是控制类的请求。Kafka 社区把 PRODUCE 和 FETCH 这类请求称为数据类请求，把 LeaderAndIsr、StopReplica 这类请求称为控制类请求

社区于 2.3 版本正式实现了数据类请求和控制类请求的分离。Kafka Broker 启动后，会在后台分别两套创建网络线程池和 IO 线程池，它们分别处理数据类请求和控制类请求