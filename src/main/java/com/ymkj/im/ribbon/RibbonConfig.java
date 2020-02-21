package com.ymkj.im.ribbon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfig {

    @ImLoadBalance
    @Bean
    public UserDao userDao01(){
        return new UserDao();
    }

    @Bean
    public UserDao userDao02(){
        return new UserDao();
    }



}
