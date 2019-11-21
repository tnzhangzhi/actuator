package com.practice.actutor;

import com.practice.actutor.springtest.conditon.ConditionOnClassDemo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ApplicationContextRunnerTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner().withConfiguration(AutoConfigurations.of(ConditionOnClassConfiguration.class));

    @Configuration
    @ConditionalOnMissingClass("com.practice.actutor.springtest.conditon.ConditionOnClassDemo")
    protected static class ConditionOnMissingClassConfiguration {

        @Bean
        public String missing(){
            return "This is missed when ConditionalOnClassDemo is present on the classpath";
        }
    }

    @Configuration
    @ConditionalOnClass(ConditionOnClassDemo.class)
    protected static class ConditionOnClassConfiguration {

        @Bean
        public String created(){
            return "This is created when ConditionalOnClassDemo is present on the classpath";
        }
    }

    @Test
    public void whenDependentClassIsPresent_thenBeanCreated() {
        this.contextRunner
                .run(context -> {
                    System.out.println(context.getBean("created"));
                });
    }

}
