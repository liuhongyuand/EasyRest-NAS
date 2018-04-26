package com.easyrest.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * String utils
 * Created by liuhongyu.louie on 2016/10/1.
 */
public class StringUtils{

    public static String replaceNull(String str){
        if (isEmptyString(str) || str.equalsIgnoreCase("null")){
            return "";
        }
        return str;
    }

    public static boolean isEmptyString(String str){
        return str == null || str.isEmpty() || str.equalsIgnoreCase("null");
    }

    public static String[] split(String string, String splitString){
        if (string.contains(splitString)){
            List<String> pathList = new ArrayList<>();
            for (String path : string.split(splitString)) {
                if (path != null && path.length() > 0){
                    pathList.add(path);
                }
            }
            return pathList.toArray(new String[]{});
        } else {
            return new String[]{string};
        }
    }

}
