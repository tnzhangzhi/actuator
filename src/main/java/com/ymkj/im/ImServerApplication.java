package com.ymkj.im;

import com.ymkj.im.ribbon.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@RestController
@SpringBootApplication
@ConditionalOnClass({UserDao.class})
public class ImServerApplication {

    @Autowired
    UserService userService;

    @ImLoadBalance
    @Autowired
    private List<UserDao> list = Collections.EMPTY_LIST;

    public static void main(String[] args) {
        SpringApplication.run(ImServerApplication.class, args);
    }

    @Bean
    public SmartInitializingSingleton singletonInitia(ObjectProvider<List<UserCustomerzi>> lists){
        return ()->{
            lists.ifAvailable((userCustomerzis -> {
                Iterator<UserDao> it1 = list.iterator();
                while (it1.hasNext()){
                    UserDao userDao = it1.next();
                    Iterator<UserCustomerzi> it2 = userCustomerzis.iterator();
                    while(it2.hasNext()){
                        UserCustomerzi c = it2.next();
                        c.customerzi(userDao);
                    }
                }
            }));
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public UserCustomerzi userCustomerzi(){
        return (userDao)->{
            List<String> users = userDao.getUsers();
            users.clear();
            users.add("hello world");
            userDao.setUsers(users);
        };
    }

    @RequestMapping("/im")
    public String index(){
        return userService.getUser();
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
