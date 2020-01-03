package com.ymkj.im.pipeline;

public class BusinessService {
    private String name;
    private boolean next;

    public BusinessService(String name,boolean next){
        this.name = name;
        this.next = next;
    }
    public boolean check(){
        System.out.println(name+"我执行完毕");
        return next;
    }

}
