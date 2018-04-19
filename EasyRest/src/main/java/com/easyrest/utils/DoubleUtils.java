package com.easyrest.utils;

/**
 * Created by liuhongyu.louie on 2017/1/15.
 */
public class DoubleUtils {

    public static double valueOf(String string){
        try {
            return Double.valueOf(string);
        } catch (NumberFormatException | NullPointerException e){
            return 0.0;
        }
    }
}
