package com.ymkj.im.pipeline;

public class TestController {
    public static void main(String[] args) {
        Pipeline pipeline = new Pipeline();
        pipeline.addLast(new BusinessService("身份证_今始",true));
        pipeline.addLast(new BusinessService("身份证_兰迪",true));
        pipeline.addLast(new BusinessService("身份证_小视",false));
        pipeline.addLast(new BusinessService("身份证_小安",true));
        pipeline.request();
    }
}
