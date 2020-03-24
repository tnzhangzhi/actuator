查看磁盘信息
df -h
fdisk -l

查看磁盘IO信息
iostat -d 1 1
tps: 每秒进程下发的IO读、写请求数量
KB_read/s: 每秒从驱动器读入的数据量，单位为K。
KB_wrtn/s: 每秒从驱动器写入的数据量，单位为K。
KB_read: 读入数据总量，单位为K。
KB_wrtn: 写入数据总量，单位为K。

iostat -x -k -d 1 2
-d：单独输出Device结果，不包括cpu结果
-k/-m：输出结果以kB/mB为单位，而不是以扇区数为单位
-x:输出更详细的io设备统计信息
interval/count：每次输出间隔时间，count表示输出次数，不带count表示循环输出


rrqm/s: 每秒对该设备的读请求被合并次数，文件系统会对读取同块(block)的请求进行合并
wrqm/s: 每秒对该设备的写请求被合并次数
r/s: 每秒完成的读次数
w/s: 每秒完成的写次数
rkB/s: 每秒读数据量(kB为单位)
wkB/s: 每秒写数据量(kB为单位)
avgrq-sz:平均每次IO操作的数据量(扇区数为单位)
avgqu-sz: 平均等待处理的IO请求队列长度
await: 平均每次IO请求等待时间(包括等待时间和处理时间，毫秒为单位)
svctm: 平均每次IO请求的处理时间(毫秒为单位)
%util: 采用周期内用于IO操作的时间比率，即IO队列非空的时间比率
 重点关注参数
1、iowait% 表示CPU等待IO时间占整个CPU周期的百分比，如果iowait值超过50%，或者明显大于%system、%user以及%idle，表示IO可能存在问题。
2、avgqu-sz 表示磁盘IO队列长度，即IO等待个数。
3、await 表示每次IO请求等待时间，包括等待时间和处理时间
4、svctm 表示每次IO请求处理的时间
5、%util 表示磁盘忙碌情况，一般该值超过80%表示该磁盘可能处于繁忙状态。