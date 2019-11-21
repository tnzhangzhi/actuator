package com.practice.actutor.thread;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ThreadTest{

//    @PostConstruct
    public void init() throws InterruptedException {
        System.out.println("===========================================================");
        ThreadDead01 t1= new ThreadDead01("zz-test-01");
        ThreadDead02 t2 = new ThreadDead02("zz-test-02");
        byte[] a = new byte[]{};
        t2.setA(a);
        t1.setA(a);
        t1.start();
        Thread.sleep(1000);
        t2.start();
    }
}
class ThreadDead01 extends Thread{
    byte[] a ;
    public ThreadDead01(String name){
        super(name);
    }

    public void setA(byte[] a) {
        this.a = a;
    }

    @Override
    public void run() {
        test();
    }

    public  void test(){
        synchronized (a) {
            try {
                Thread.sleep(500);
                System.out.println("zz-test-01 进入（Dead01）wait set");
                a.wait();
                System.out.println("zz-test-01 从（Dead01）wait set 出来，重新执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class ThreadDead02 extends Thread{
    byte[]  a;
    public ThreadDead02(String name){
        super(name);
    }
    public void setA(byte[] a){
        this.a = a;
    }
    @Override
    public void run() {
        test();
    }
    public  void test(){
        synchronized (a) {
            try {
                System.out.println("zz-test-02 要睡60秒");
                for (int i = 0; i < 20; i++) {
                    Thread.sleep(1000);
                    System.out.println("zz-test-02 睡了" + (i + 1) + "秒");
                }
                System.out.println("叫醒zz-test-01");
                a.notifyAll();
                Thread.sleep(5000);
                System.out.println("zz-test-02 我结束了");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}