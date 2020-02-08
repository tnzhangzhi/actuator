package com.ymkj.im;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ImServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImServerApplication.class, args);
    }

//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
//        return restTemplateBuilder.build();
//    }
//
//    @Bean
//    public CommandLineRunner run(RestTemplate restTemplate){
//        return args -> {
//            String s = restTemplate.getForObject("https://api.shumaidata.com",String.class);
//            System.out.println(s);
//        };
//    }

}
