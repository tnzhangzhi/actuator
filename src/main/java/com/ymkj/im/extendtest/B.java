package com.ymkj.im.extendtest;

public class B extends A {
    public B(){
        super();
    }

    @Override
    public void print() {
        System.out.println("BBBBBB");
    }

    public static void main(String[] args) {
        B b = new B();
    }

}
