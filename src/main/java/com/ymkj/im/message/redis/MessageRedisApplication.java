package com.ymkj.im.message.redis;

import com.ymkj.im.ImServerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.concurrent.CountDownLatch;

@Configuration
public class MessageRedisApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageRedisApplication.class);

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter,new PatternTopic("chat"));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Reciever reciever){
        return new MessageListenerAdapter(reciever,"receiveMessage");
    }

    @Bean
    Reciever reciever(CountDownLatch latch){
        return new Reciever(latch);
    }

    @Bean
    CountDownLatch latch(){
        return new CountDownLatch(1);
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory){
        return new StringRedisTemplate(connectionFactory);
    }

//    public static void main(String[] args) throws InterruptedException {
//        ApplicationContext ctx = SpringApplication.run(ImServerApplication.class,args);
//        StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
//        CountDownLatch latch = ctx.getBean(CountDownLatch.class);
//        LOGGER.info("Sending message ....");
//        System.out.println("Sending message ....");
//        template.convertAndSend("chat","Hello from Redis");
//        latch.await();
//        System.exit(0);
//    }
}
