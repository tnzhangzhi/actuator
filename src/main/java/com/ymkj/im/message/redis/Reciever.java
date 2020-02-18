package com.ymkj.im.message.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;

public class Reciever {

    private static final Logger LOGGER = LoggerFactory.getLogger(Reciever.class);

    private CountDownLatch latch;

    @Autowired
    public Reciever(CountDownLatch latch){
        this.latch = latch;
    }

    public void receiveMessage(String message){
        LOGGER.info("Received <"+message+">");
        System.out.println("Received <"+message+">");
        latch.countDown();
    }
}
