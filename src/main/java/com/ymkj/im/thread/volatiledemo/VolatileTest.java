package com.ymkj.im.thread.volatiledemo;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile 只能保证可见性 无法保证原子性
 */
public class VolatileTest {
    private static int x = 0;
    private static volatile boolean flag = false;

    private static void writer(){
        x = 123;
        flag = true;
    }

    private static void reader(){
        if(flag){
            System.out.println(x);
        }
    }

    static class Volatile implements Runnable{
        private volatile boolean flag = true;
        @Override
        public void run() {
            while(flag){

            }
            System.out.println(Thread.currentThread().getName()+"执行完毕");
        }

        private void stopThread(){
            flag = false;
        }
    }



    public static void main(String[] args) {
        writer();
        reader();
        Volatile a = new Volatile();
        new Thread(a,"thread test").start();
        System.out.println(Thread.currentThread().getName()+"正在执行");
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            String value = sc.next();
            if("1".equals(value)){
                new Thread(a::stopThread).start();
                break;
            }
        }
        System.out.println(Thread.currentThread().getName()+"退出了");
    }

    static class VolatileIncr implements Runnable{

        private volatile static int count=0;
//        private static AtomicInteger a = new AtomicInteger(0);

        @Override
        public void run() {
            for(int i=0;i<10000;i++) {
                count++;
//                a.incrementAndGet();
            }
        }

        public static void main(String[] args) throws InterruptedException {
            VolatileIncr v = new VolatileIncr();
            Thread t1 = new Thread(v,"t1");
            Thread t2 = new Thread(v,"t2");
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            System.out.println(count);
//            System.out.println(a.get());
        }
    }

}
