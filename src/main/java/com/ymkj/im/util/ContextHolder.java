package com.ymkj.im.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(ContextHolder.applicationContext == null){
            ContextHolder.applicationContext = applicationContext;
        }
    }

    public static Object get(String name){
        return applicationContext.getBean(name);
    }

    public static Object get(Class clz){
        return applicationContext.getBean(clz);
    }

    public static <T> T getBean(Class<T> clz) {
        return applicationContext.getBean(clz);
    }
}
