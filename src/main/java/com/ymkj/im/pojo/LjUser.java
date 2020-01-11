package com.ymkj.im.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class LjUser {

    private Long id;
    private String name;
    private String password;
    private String mobile;
    private String token;
    private String ip;
    private String city;
    private String country;
    private String status;
    private String point;
    private String create_time;
    private Date lastLoginTime;
}
