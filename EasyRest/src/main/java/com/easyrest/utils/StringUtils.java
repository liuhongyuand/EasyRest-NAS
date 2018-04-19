package com.easyrest.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * String utils
 * Created by liuhongyu.louie on 2016/10/1.
 */
public class StringUtils{
    private static final String IntegerRegex = "^-?[0-9]+$";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    private static final DateFormat DATE_FORMAT_WITHOUT_MILLI = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String replaceNull(String str){
        if (isEmptyString(str) || str.equalsIgnoreCase("null")){
            return "";
        }
        return str;
    }

    public static boolean isEmptyString(String str){
        return str == null || str.isEmpty() || str.equalsIgnoreCase("null");
    }

    public static boolean isIntegerString(String string) {
        return string != null && string.matches(IntegerRegex);
    }

    public static Date formatDateString(String dateString){
        try {
            return DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            try {
                return DATE_FORMAT_WITHOUT_MILLI.parse(dateString);
            } catch (ParseException e1) {
                return null;
            }
        }
    }

    public static String getRandomCaptcha(){
        return UUID.randomUUID().toString().substring(0, 5);
    }

    public static String getTimeWithUUID(){
        return DateUtils.getNow() + "_" + UUID.randomUUID().toString();
    }

}
