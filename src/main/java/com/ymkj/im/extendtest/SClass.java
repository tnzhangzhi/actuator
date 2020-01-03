package com.ymkj.im.extendtest;

public class SClass extends PClass{
    public SClass(){
        System.out.println("子类初始化");
    }

    protected void prepareRefresh(){
        System.out.println("子类刷新");
        super.prepareRefresh();
    }

    public static void main(String[] args) {
        SClass s = new SClass();
        PClass p = s;
        p.test();
    }
}
