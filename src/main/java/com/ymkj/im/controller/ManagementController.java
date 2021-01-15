package com.ymkj.im.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

@RequestMapping("/index")
@RestController
public class ManagementController {

    @RequestMapping("/thread")
    public String threadInfo(){
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] tids = bean.getAllThreadIds();
        ThreadInfo[] tinfos = bean.getThreadInfo(tids);
        return JSON.toJSONString(tinfos);
    }

}
