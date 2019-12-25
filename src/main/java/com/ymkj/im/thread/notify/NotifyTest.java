package com.ymkj.im.thread.notify;

/**
 * wait 和 notify 一般用在同步代码块内持有锁的对象
 * 且调用wait后会释放锁
 */
public class NotifyTest {
    static byte[] b = new byte[0];

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            try {
                testWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread-01").start();
        new Thread(()->{
            try {
                testWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread-02").start();
        new Thread(()->{
            try {
                testWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread-03").start();
        new Thread(()->{
            try {
                testWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread-04").start();
        new Thread(()->{
            try {
                testWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread-05").start();
        new Thread(()->{
            try {
                testWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread-06").start();
        Thread.sleep(2000);
        new Thread(()->{
                testNotify();
            testNotify();
            testNotify();
            testNotify();
            testNotify();
            testNotifyAll();
        },"thread-11").start();
    }

    private static  void testWait() throws InterruptedException {
        synchronized (b) {
            System.out.println(Thread.currentThread().getName() + "开始执行");
            b.wait();
            System.out.println(Thread.currentThread().getName() + "执行完成");
        }
    }

    private static void testNotify(){
        synchronized (b){
            b.notify();
        }
    }

    private static void testNotifyAll(){
        synchronized (b){
            b.notifyAll();
        }
    }

}
