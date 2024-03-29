package tech.dbgsoftware.easyrest.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.dbgsoftware.easyrest.exception.PageNotFoundException;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class LogUtils {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(2);
    private static final Logger LOGGER = LoggerFactory.getLogger(LogUtils.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    private static Function<String, String> INFO_FUNCTION = null;
    private static Function<String, String> ERROR_FUNCTION = null;

    public static void setFunction(Function<String, String> infoFunction, Function<String, String> errorFunction) {
        INFO_FUNCTION = infoFunction;
        ERROR_FUNCTION = errorFunction;
    }

    public static String printGsonObject(final Object obj){
        String jsonResult = GSON.toJson(obj);
        LOGGER.info(jsonResult);
        return jsonResult;
    }

    public static void debug(String string, Class aClass){
        LOGGER.debug(String.format("%s %s", new Date(), "From " + aClass.getName() + ": " + string));
    }

    public static void debug(Object obj, Class aClass){
        LOGGER.debug(String.format("%s %s", new Date(), "From " + aClass.getName() + ": " + GSON.toJson(obj)));
    }

    public static void info(String string, Class aClass){
        String log = String.format("%s %s", new Date(), "From " + aClass.getName() + ": " + string);
        LOGGER.info(log);
        if (INFO_FUNCTION != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('[');
            stringBuilder.append(Thread.currentThread().getName());
            stringBuilder.append("] ");
            EXECUTOR_SERVICE.execute(() -> INFO_FUNCTION.apply(stringBuilder.append(log).toString()));
        }
    }

    public static void info(Object obj, Class aClass){
        String log = String.format("%s %s", new Date(), "From " + aClass.getName() + ": " + GSON.toJson(obj));
        LOGGER.info(log);
        if (INFO_FUNCTION != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('[');
            stringBuilder.append(Thread.currentThread().getName());
            stringBuilder.append("] ");
            EXECUTOR_SERVICE.execute(() -> INFO_FUNCTION.apply(stringBuilder.append(log).toString()));
        }
    }

    public static void info(String string){
        String log = String.format("%s %s", new Date(), string);
        LOGGER.info(log);
        if (INFO_FUNCTION != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('[');
            stringBuilder.append(Thread.currentThread().getName());
            stringBuilder.append("] ");
            EXECUTOR_SERVICE.execute(() -> INFO_FUNCTION.apply(stringBuilder.append(log).toString()));
        }
    }

    public static void info(Object obj){
        String log = String.format("%s %s", new Date(), GSON.toJson(obj));
        LOGGER.info(log);
        if (INFO_FUNCTION != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('[');
            stringBuilder.append(Thread.currentThread().getName());
            stringBuilder.append("] ");
            EXECUTOR_SERVICE.execute(() -> INFO_FUNCTION.apply(stringBuilder.append(log).toString()));
        }
    }

    public static void error(String message){
        String error = String.format("%s %s", new Date(), message + "\r\n" + ThreadStackUtils.getStackInfo());
        LOGGER.error(error);
        if (ERROR_FUNCTION != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('[');
            stringBuilder.append(Thread.currentThread().getName());
            stringBuilder.append("] ");
            EXECUTOR_SERVICE.execute(() -> ERROR_FUNCTION.apply(stringBuilder.append(error).toString()));
        }
    }

    public static void error(String message, Object object){
        if (object == null) {
            error(message + "\r\n" + ThreadStackUtils.getStackInfo());
            if (ERROR_FUNCTION != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append('[');
                stringBuilder.append(Thread.currentThread().getName());
                stringBuilder.append("] ");
                EXECUTOR_SERVICE.execute(() -> ERROR_FUNCTION.apply(stringBuilder.append(message).append("\r\n").append(ThreadStackUtils.getStackInfo()).toString()));
            }
        } else {
            String error = String.format("%s %s", new Date(), message + "\r\n" + ThreadStackUtils.getStackInfo());
            LOGGER.error(error, object);
            if (ERROR_FUNCTION != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append('[');
                stringBuilder.append(Thread.currentThread().getName());
                stringBuilder.append("] ");
                EXECUTOR_SERVICE.execute(() -> ERROR_FUNCTION.apply(stringBuilder.append(error).toString()));
            }
        }
    }

    public static void error(String message, Exception e){
        if (e instanceof PageNotFoundException) {
            error(message + " " + e.toString() + "\r\n" + ThreadStackUtils.getStackInfo(e));
            if (ERROR_FUNCTION != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append('[');
                stringBuilder.append(Thread.currentThread().getName());
                stringBuilder.append("] ");
                EXECUTOR_SERVICE.execute(() -> ERROR_FUNCTION.apply(stringBuilder.append(message).append("\r\n").append(ThreadStackUtils.getStackInfo(e)).toString()));
            }
        } else {
            String error = String.format("%s %s", new Date(), message + " " + e.toString() + "\r\n" + ThreadStackUtils.getStackInfo(e));
            LOGGER.error(error, e);
            if (ERROR_FUNCTION != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append('[');
                stringBuilder.append(Thread.currentThread().getName());
                stringBuilder.append("] ");
                EXECUTOR_SERVICE.execute(() -> ERROR_FUNCTION.apply(stringBuilder.append(message).toString()));
            }
        }
    }
}
