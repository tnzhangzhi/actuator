"zz-test-01" #69 prio=5 os_prio=31 tid=0x00007fadb1c73800 nid=0x5b03 waiting for monitor entry [0x0000700001f27000]
   java.lang.Thread.State: BLOCKED (on object monitor)
	at com.practice.actutor.thread.ThreadDead02.test(ThreadTest.java:62)
	- waiting to lock <0x0000000740899220> (a com.practice.actutor.thread.ThreadDead02)
	at com.practice.actutor.thread.ThreadDead01.test(ThreadTest.java:42)
	- locked <0x00000007408993a0> (a com.practice.actutor.thread.ThreadDead01)
	at com.practice.actutor.thread.ThreadDead01.run(ThreadTest.java:33)
zz-test-02 同上互相锁住和等待

nid（16进制线程ID） ：native thread id. 每一个nid对应于linux下的一个tid, 即lwp  (light weight process, or thread).
tid（线程java内存地址） Java memory address of its internal Thread control structure
linux下的lwp可以在/proc/pid/task/下查看
jstack中的nid是十六进制数，task目录下为十进制数，需要转换一致后才可以比较。
得到进程ID为21711，第二步找出该进程内最耗费CPU的线程，可以使用ps -Lfp pid或者ps -mp pid -o THREAD, tid, time或者top -Hp pid  
printf %x xxxx
jstack mmm |grep nnnn
inux下的lwp也可以通用ps来查看
ps -mp pid号 -o THREAD,tid,lwp,nlwp,time,rss,size,%mem
jmap -histo:live pid |head -20
java  -XX:+PrintCommandLineFlags  -version 查看jvm使用的算法


日志搜索
cat -n all.log |grep "NullPointerException" -C 10
