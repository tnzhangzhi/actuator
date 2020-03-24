基于异步事件驱动，高性能主要来自于其I/O模型和线程处理模型，前者决定如何收发数据，后者决定如何处理数据

Caused by: java.net.SocketException: Too many open files in system
ulimit -a 查看 open files 数量，默认65535
ulimit -n 100000 设置为十万
查看系统已经打开的文件数，和最大可以打开的文件数
cat  /proc/sys/fs/file-nr


Caused by: java.net.BindException: Cannot assign requested address
cat /proc/sys/net/ipv4/ip_local_port_range 默认开启的随机端口范围只有2万左右
修改文件 /etc/sysctl.conf
添加一行
net.ipv4.ip_local_port_range = 15535 65535

查看各种状态的tcp链接数量
netstat -n | awk '/^tcp/ {++state[$NF]} END {for(key in state) print key,"\t",state[key]}'