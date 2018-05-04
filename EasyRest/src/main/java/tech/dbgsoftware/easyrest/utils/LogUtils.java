package tech.dbgsoftware.easyrest.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class LogUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogUtils.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    public static String printGsonObject(final Object obj){
        String jsonResult = GSON.toJson(obj);
        LOGGER.info(jsonResult);
        return jsonResult;
    }

    public static void info(String string){
        LOGGER.info(String.format("%s %s", String.valueOf(new Date()), string));
    }

    public static void info(Object obj){
        LOGGER.info(String.format("%s %s", String.valueOf(new Date()), GSON.toJson(obj)));
    }

    public static void debug(String string, Class aClass){
        LOGGER.debug(String.format("%s %s", String.valueOf(new Date()), "From " + aClass.getName() + ": " + string));
    }

    public static void debug(Object obj, Class aClass){
        LOGGER.debug(String.format("%s %s", String.valueOf(new Date()), "From " + aClass.getName() + ": " + GSON.toJson(obj)));
    }

    public static void info(String string, Class aClass){
        LOGGER.info(String.format("%s %s", String.valueOf(new Date()), "From " + aClass.getName() + ": " + string));
    }

    public static void info(Object obj, Class aClass){
        LOGGER.info(String.format("%s %s", String.valueOf(new Date()), "From " + aClass.getName() + ": " + GSON.toJson(obj)));
    }

    public static void error(String message, Object object){
        LOGGER.error(String.format("%s %s", String.valueOf(new Date()), message), object);
    }

    public static void error(String message, Exception e){
        LOGGER.error(String.format("%s %s", String.valueOf(new Date()), message), e);
    }
}
