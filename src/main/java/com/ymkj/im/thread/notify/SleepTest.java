package com.ymkj.im.thread.notify;

/**
 * 同步代码块中sleep不会释放锁
 * 执行线程的interrupt会给线程中断标识置为true，中断后会置为false，默认为false
 * isInterrupted()会返回线程是否中断
 * interrupt并不一定会中断，只是设置了中断标识，在sleep和wait等情况系会中断并抛出isInterrupted()
 * Thread.interrupted()，线程不能调用该方法，执行中的当前线程通过该方法可以检测中断标识，如果中断，就会清除中断标识
 */
public class SleepTest {

    static byte[] b = new byte[0];

    public static void main(String[] args) {
        Thread t1= new Thread(()->{
            testSleep();
        },"thread-01");
        Thread t2 = new Thread(()->{
            testSleep();
        },"thread-02");
        t1.start();
        t2.start();
        new Thread(()->{
            System.out.println(t1.isInterrupted());
            t1.interrupt();
            System.out.println(t1.isInterrupted());
        },"thread-03").start();

        new Thread(()->{
            System.out.println("分割线---------------");
            testThreadInterrupt();
        },"thread-04").start();
    }

    private static void testSleep(){
        synchronized (b){
            try {
                System.out.println(Thread.currentThread().getName() + "开始执行");
                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName() + "执行完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void testThreadInterrupt(){
        System.out.println("1："+Thread.currentThread().isInterrupted());//默认false
        System.out.println("2："+Thread.interrupted()); //默认false
        Thread.currentThread().interrupt();
        System.out.println("3："+Thread.interrupted()); //true,已中断，并清楚中断标识
        System.out.println("4："+Thread.interrupted());//因为上面清楚了，所以此处false
        System.out.println("5："+Thread.currentThread().isInterrupted());//因为清除了，此处也为false
    }
}
