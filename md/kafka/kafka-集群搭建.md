环境准备
先搭建zookeeper集群
1.下载kafka并解压到/opt下，并复制三份kafka01，kafka02，kafka03
2.编辑zookeeper.properties
tickTime=2000
initLimit=10
syncLimit=5
dataDir=/opt/kafka/zookeeper/data/server0x                                                                                                  
clientPort=2181
server.1=127.0.0.1:2887:3887                                                                                                       
server.2=127.0.0.1:2888:3888                                                                                                       
server.3=127.0.0.1:2889:3889 

3.创建myid文件
在server0x目录下面创建myid文件对应server.x

3.启动zk脚本
#!/bin/bash
/opt/kafka/kafka01/bin/zookeeper-server-start.sh -daemon /opt/kafka/kafka01/config/zookeeper.properties
/opt/kafka/kafka02/bin/zookeeper-server-start.sh -daemon /opt/kafka/kafka02/config/zookeeper.properties
/opt/kafka/kafka03/bin/zookeeper-server-start.sh -daemon /opt/kafka/kafka03/config/zookeeper.properties

4.编辑kafka配置文件，添加如下配置
broker.id=1  不能重复
listeners=PLAINTEXT://0.0.0.0:9091   监听地址，同一台机器上端口好不同
advertised.listeners=PLAINTEXT://127.0.0.1:9091  注册到zk中地址，同一台机器上端口号不同
log.dirs=/opt/kafka/logs/kafka01/kafka-logs
zookeeper.connect=127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183

5.启动脚本
#!/bin/bash
/opt/kafka/kafka01/bin/kafka-server-start.sh -daemon /opt/kafka/kafka01/config/server.properties
/opt/kafka/kafka02/bin/kafka-server-start.sh -daemon /opt/kafka/kafka02/config/server.properties
/opt/kafka/kafka03/bin/kafka-server-start.sh -daemon /opt/kafka/kafka03/config/server.properties

6.查看集群状态
./zookeeper-shell.sh 127.0.0.1:2181

