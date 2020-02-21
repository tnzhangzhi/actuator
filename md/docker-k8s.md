1.安装kubectl
Kubectl是Kubernetes的命令行。您可以从Google存储空间安装它
curl -Lo /usr/local/bin/kubectl https://storage.googleapis.com/kubernetes-release/release/v1.15.3/bin/linux/amd64/kubectl && chmod +x /usr/local/bin/kubectl

2.安装kind
Kind是用于在Docker中运行Kubernetes的工具。它非常适合集成测试或资源受限的简单开发用例。您可以从github安装它：
curl -Lo /usr/local/bin/kind https://github.com/kubernetes-sigs/kind/releases/download/v0.5.1/kind-linux-amd64 && chmod +x /usr/local/bin/kind

3.并运行它以创建一个（单节点）Kubernetes集群：
kind create cluster

4.然后设置凭据以连接到集群：

mkdir -p ~/.kube && kind get kubeconfig > ~/.kube/config

5.检查它是否有效：

kubectl get all
NAME                 TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)    AGE
service/kubernetes   ClusterIP   10.43.0.1       <none>        443/TCP    2d18h
现在我们可以部署我们的Spring Boot应用程序。

将应用程序部署到Kubernetes
我们已经准备了一个简单的（空）Spring Boot应用程序并将其部署到Dockerhub。您可以运行它来检查它是否有效：

docker run -p 8080:8080 springguides/demo

一旦启动，您可以检查它是否在另一个终端上正常工作：

curl localhost:8080/actuator/health

准备通过杀死本地容器进行部署：

echo "Send Ctrl+C to kill the container"

部署到Kubernetes
因此，您有一个运行并公开端口8080的容器，因此，要使Kubernetes运行，您所需要的只是一些YAML。为了避免不得不查看或编辑YAML，现在，您可以要求kubectl为您生成它。唯一可能有所不同的是--image名称。如果将容器部署到自己的存储库，请使用其标记而不是以下标记：

kubectl create deployment demo --image=springguides/demo --dry-run -o=yaml > deployment.yaml

echo --- >> deployment.yaml

kubectl create service clusterip demo --tcp=8080:8080 --dry-run -o=yaml >> deployment.yaml

您可以采用上面生成的YAML并根据需要进行编辑，也可以仅应用它：

kubectl apply -f deployment.yaml

deployment.apps/demo created
service/demo created
检查应用程序是否正在运行：

kubectl get all

NAME                             READY     STATUS      RESTARTS   AGE
pod/demo-658b7f4997-qfw9l        1/1       Running     0          146m

NAME                 TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)    AGE
service/kubernetes   ClusterIP   10.43.0.1       <none>        443/TCP    2d18h
service/demo         ClusterIP   10.43.138.213   <none>        8080/TCP   21h

NAME                   READY     UP-TO-DATE   AVAILABLE   AGE
deployment.apps/demo   1/1       1            1           21h

NAME                              DESIRED   CURRENT   READY     AGE
replicaset.apps/demo-658b7f4997   1         1         1         21h
d
提示：继续操作，kubectl get all直到演示窗格显示其状态为“正在运行”。

现在，您需要能够连接到在Kubernetes中作为服务公开的应用程序。一种在开发时有效的方法是创建SSH隧道：

kubectl port-forward svc/demo 8080:8080

然后您可以验证该应用程序正在运行：

curl localhost:8080/actuator/health

{"status":"UP"}