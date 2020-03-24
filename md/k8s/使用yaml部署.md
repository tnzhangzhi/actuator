第一步：创建部署
部署对象是最常见的Kubernetes对象之一。部署对象定义了所需的容器规范以及Kubernetes其他部分用来发现并连接到应用程序的名称和标签

任务
将以下定义复制到编辑器。该定义定义了如何使用在端口80上运行镜像test/springboot
启动名为webapp1的应用程序
apiVersion: apps/v1
kind: Deployment
metadata:
  name: webapp1
spec:
  replicas: 1
  selector:
    matchLabels:
      app: webapp1
  template:
    metadata:
      labels:
        app: webapp1
    spec:
      containers:
      - name: webapp1
        image: tnzhangzhi/springboot
        ports:
        - containerPort: 8080

使用一下命令将其部署到集群
kubectl create -f deployment.yaml

由于是部署对象，因此可以通过一下方式获取所有已部署的对象列表
kubectl get deployment
可以使用以下命令输出单个部署的详细信息
kubectl describe deployment webapp1

第二步：创建服务
Kubernetes具有强大的联网功能，可控制应用程序的通信方式。这些网络配置也可以通过YAML进行控制。

任务
将服务定义复制到编辑器。该服务选择带有标签webapp1的所有应用程序。部署多个副本或实例时，
它们将基于此通用标签自动进行负载均衡。该服务可通过NodePort使应用程序可用
apiVersion: v1
kind: Service
metadata:
  name: webapp1-svc
  labels:
    app: webapp1
spec:
  type: NodePort
  ports:
  - port: 80
    nodePort: 30080
  selector:
    app: webapp1
    
使用Kubectl以一致的方式部署所有Kubernetes对象。
通过一下方式部署服务
kubectl create -f service.yaml

和以前一样，使用部署的所有Service对象的详细信息kubectl get svc。通过描述对象，可以发现有关配置的更多详细信息kubectl describe svc webapp1-svc。

curl host01:30080

第3步-规模部署
可以更改YAML的详细信息，因为部署需要不同的配置。这遵循了基础架构的代码思维方式。清单应保留在源代码管理下，并用于确保生产中的配置与源代码管理中的配置相匹配。

任务
更新deployment.yaml文件以增加运行的实例数。例如，文件应如下所示：

replicas: 4
使用kubectl apply可以应用对现有定义的更新。要缩放副本数量，请使用部署更新的YAML文件。kubectl apply -f deployment.yaml

即时，我们集群的所需状态已更新，可以通过以下方式查看 kubectl get deployment

将会安排其他Pod以匹配请求。 kubectl get pods

由于所有Pod具有相同的标签选择器，因此它们将在部署的Service NodePort之后进行负载平衡。

向端口发出请求将导致不同的容器处理请求 curl host01:30080

其他Kubernetes网络细节和对象定义将在以后的场景中介绍。

继续