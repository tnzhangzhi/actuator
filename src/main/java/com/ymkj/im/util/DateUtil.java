package com.ymkj.im.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String format(Date date, String format){
        if(date==null){
            throw new RuntimeException();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String str = simpleDateFormat.format(date);
        return str;
    }
}
