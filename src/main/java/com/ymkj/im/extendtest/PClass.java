package com.ymkj.im.extendtest;

public class PClass {
    public PClass(){
        System.out.println("父类初始化");
    }

    protected void prepareRefresh(){
        System.out.println("父类刷新");
    }

    public void test(){
        this.prepareRefresh();
    }
}
