package com.ymkj.im.datastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 100元买100🐔
 * 公鸡5元，母鸡3元，3只小鸡1元
 * 求各有多少只
 * 5a+3b+c/3=100
 * a+b+c=100
 * c%3=0
 */
public class BaiJi {

    //n 鸡的数目
    public List chicken(int n){
        List<Map<String,Integer>> list = new ArrayList<>();
        for(int a=0;a<n;a++){
            for(int b=0;b<n;b++){
                for(int c=0;c<n;c++){
                    if((a+b+c==n) && (5*a+3*b+c/3==n) && c%3==0){
                        Map<String,Integer> map = new HashMap<>();
                        map.put("big",a);
                        map.put("middle",b);
                        map.put("small",c);
                        list.add(map);
                    }
                }
            }
        }
        return list;
    }

    public static void main(String[] args) {
        BaiJi baiJi = new BaiJi();
        List list = baiJi.chicken(80);
        System.out.println(list.toString());
    }
}
