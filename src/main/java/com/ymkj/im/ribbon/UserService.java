package com.ymkj.im.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao01;

    public String getUser(){
        return userDao01.request();
    }

}
