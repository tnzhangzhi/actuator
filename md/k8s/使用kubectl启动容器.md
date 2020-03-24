第一步：启动集群
minikube start --wait=false
检查节点节点准备就绪状态
kubectl get nodes

第二步：运行kubectl
运行命令创建基于指定参数的部署（deployment），部署将发布到kubernetes主服务器，后者启动所需的Pod和容器
kubectl run 类似集群级别搬运工
该命令格式: kubectl run <部署名称(name of deployment)> <properties>

任务（task） 
以下命令将启动一个名为http的部署，该部署将基于镜像test/springboot启动一个容器
kubectl run http --image=tnzhangzhi/springboot --replicas=1
然后你可以是kubectl 查看部署状态
kubectl get deployments
查看kubernetes 创建部署过程描述
kubectl describe deployment http
描述包括可用副本数，指定的标签以及与部署关联的事件，这些事件将突出显示可能发生的任何问题和错误

第三步：暴露部署服务
创建部署后，我们可以使用kubectl创建一个服务，该服务在特定端口上公开Pod
通过kubectl暴露公开新部署的http，该命令允许您定义服务的参数及如何公开

任务
使用一下命令暴露容器端口80到主机的外部ip的8000端口
kubectl expose deployment http --external-ip="192.168.100.7" --port=8080 --target-port=8080
然后，你可以通过ping通主机并查看HTTP服务的结果
curl http://172.17.0.29:8000

第四步：运行和暴露
使用kubectl单个命令运行和公开部署

任务
使用命令创建在端口8001上暴露第二个http服务。
kubectl run httpexposed --image=tnzhangzhi/springboot --replicas=1 --port=80 --hostport=8001
你可以测试服务
curl http://172.17.0.29:8001
在后台，这通过docker port mapping 暴露pod
结果，你将不会看到列出使用的服务
kubectl get svc
查找使用的详细信息
docker ps | grep httpexposed

运行上面的命令，您会发现端口暴露在Pod上，而不是http容器本身。暂停容器，负责为Pod定义网络。
容器中的其他容器共享相同的网络名称空间。这样可以提高网络性能，并允许多个容器通过同一网络接口进行通信。

第五步：容器伸缩
随着部署的运行，我们可以使用kubectl扩展副本的数量
扩展部署将要求kubernetes启动其它pod。然后，将使用的公开服务自动平衡这些pod的负载

任务
命令kubectl scale允许我们调整特定部署或控制器运行的Pod数量
kubectl scale --replicas=3 deployment http
列出所有pod，你应该会看到三个正在运行的http部署
kubectl get pods
每个pod启动后，它将添加到负载均衡器服务中。通过服务描述，您可以查看端点和所包含的关联pod
kubectl describe svc http
向服务发出请求将在处理请求的不同节点中请求
curl http://172.17.0.29:8000
