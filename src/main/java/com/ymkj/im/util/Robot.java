package com.ymkj.im.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

public class Robot {
    private final String API_KEY = "23e7c2916c6849368ff3d8da0833d728";
    private final String SECRET = "273c5ceac727af4c";
    private final static String TL_URL = "http://api.qingyunke.com/api.php?key=free&appid=0&msg=";

    public static String send(String msg) {
        try {
            String ret = HttpUtil.get(TL_URL+msg,null);
            JSONObject obj = JSON.parseObject(ret);
            if(obj.containsKey("content")){
                return obj.getString("content");
            }
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
