package com.ymkj.im.beanfactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("自定义BeanFactoryPostProcessor" + configurableListableBeanFactory);
        String[] names = configurableListableBeanFactory.getBeanDefinitionNames();
        for(String name : names){
            BeanDefinition bd = configurableListableBeanFactory.getBeanDefinition(name);
//            System.out.println(bd.getBeanClassName());
        }
    }
}
