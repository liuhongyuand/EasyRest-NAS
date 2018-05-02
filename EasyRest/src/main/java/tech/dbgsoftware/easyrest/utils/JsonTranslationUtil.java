package tech.dbgsoftware.easyrest.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class JsonTranslationUtil {

    private static final Gson GSON = new GsonBuilder().create();

    public static String toJsonString(Object object){
        return GSON.toJson(object);
    }

    public static <T> T fromJson(String jsonString, Class<T> aClass){
        return GSON.fromJson(jsonString, aClass);
    }

    public static <T> T fromJson(String jsonString, Type type){
        try {
            return GSON.fromJson(jsonString, type);
        } catch (Exception e){
            return null;
        }
    }

}
