1.新建springboot工程名dockers

2.在pom同目录下创建Dockerfile文件，内容如下：

FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

3.生成镜像并运行
$ docker build -t test/springboot .
$ docker run -p 8080:8080 test/springboot

4.如果想进入镜像内部浏览，可以这样打开，容器没有bash，具有某些bash命令，但不是全部
$ docker run -ti --entrypoint /bin/sh test/springboot

5.如果你有一个正在运行的容器，并且想窥视他，可以是用docker exec
$ docker run --name myapp -ti --entrypoint /bin/sh myorg/myapp
$ docker exec -ti myapp /bin/sh