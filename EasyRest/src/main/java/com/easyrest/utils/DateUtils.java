package com.easyrest.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by liuhongyu.louie on 2016/10/16.
 */
public class DateUtils {

    private DateUtils(){}

    public static long getNow(){
        return new Date().getTime();
    }

    public static Timestamp dateToTimeStamp(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

}
