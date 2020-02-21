package com.ymkj.im.ribbon;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class UserDao {

    List<String> users = new ArrayList<>(Arrays.asList("123"));

    public String request(){
        String s = users.get(0);
        return s;
    }
}
