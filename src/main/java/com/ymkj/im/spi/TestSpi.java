package com.ymkj.im.spi;

import java.util.ServiceLoader;

public class TestSpi {
    public static void main(String[] args) {
        ServiceLoader<Isay> loader = ServiceLoader.load(Isay.class);
        for(Isay isay : loader){
            isay.say();
        }
    }
}
