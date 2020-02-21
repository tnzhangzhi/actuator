1.安装minikube
curl -Lo minikube https://storage.googleapis.com/minikube/releases/v0.29.0/minikube-darwin-amd64 && chmod +x minikube && sudo cp minikube /usr/local/bin/ && rm minikube

2.minikube version

3.通过运行minikube start命令来启动集群：

minikube start --wait=false
中国使用如下，被墙了：
minikube start --image-repository='registry.cn-hangzhou.aliyuncs.com/google_containers'

大！现在，您的在线终端中有一个正在运行的Kubernetes集群。Minikube为您启动了虚拟机，并且Kubernetes集群现在正在该VM中运行。

4.可以使用kubectl CLI 与集群进行交互。这是用于管理Kubernetes和在集群顶部运行的应用程序的主要方法。

群集的详细信息及其运行状况可以通过以下方式发现 kubectl cluster-info

5.使用以下命令查看集群中的节点 kubectl get nodes

如果该节点标记为“未就绪”，则它仍在启动组件。

此命令显示可用于承载我们的应用程序的所有节点。现在我们只有一个节点，我们可以看到它的状态为就绪（可以接受要部署的应用程序）。


6.有了运行中的Kubernetes集群，现在就可以部署容器了。

使用kubectl run，它可以将容器部署到群集上-kubectl create deployment first-deployment --image=katacoda/docker-http-server

可以通过运行中的Pods发现部署状态- kubectl get pods

容器运行后，可以根据要求通过不同的网络选项进行公开。一种可能的解决方案是NodePort，它为容器提供了动态端口。

kubectl expose deployment first-deployment --port=80 --type=NodePort

下面的命令查找分配的端口并执行HTTP请求。

export PORT=$(kubectl get svc first-deployment -o go-template='{{range.spec.ports}}{{if .nodePort}}{{.nodePort}}{{"\n"}}{{end}}{{end}}')
echo "Accessing host01:$PORT"
curl host01:$PORT

结果是处理请求的容器。