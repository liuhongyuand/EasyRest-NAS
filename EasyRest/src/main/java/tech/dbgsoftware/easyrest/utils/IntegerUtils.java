package tech.dbgsoftware.easyrest.utils;

/**
 * Created by liuhongyu.louie on 2017/1/15.
 */
public class IntegerUtils {

    public static int valueOf(String string){
        try{
            return Integer.valueOf(string);
        } catch (NumberFormatException | NullPointerException e){
            return 0;
        }
    }

}
