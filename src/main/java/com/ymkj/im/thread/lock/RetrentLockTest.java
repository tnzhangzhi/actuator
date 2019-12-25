package com.ymkj.im.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class RetrentLockTest {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+"正在执行");
                lock.lock();
                for(int i=0;i<60;i++) {
                    System.out.println(Thread.currentThread().getName()+">>>"+(i+1));
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        },"线程01").start();
        Thread.sleep(2000);

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"正在执行");
            try{
                lock.lock();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName()+"正在执行");
            }
        },"线程02").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"正在执行");
            try{
                lock.lock();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName()+"执行完毕");
            }
        },"线程03").start();
    }
}
