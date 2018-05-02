package tech.dbgsoftware.easyrest.annotations.method;

/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
public enum RequestMethod {

    POST("POST", Post.class),

    GET("GET", Get.class),

    PUT("PUT", Put.class),

    DELETE("DELETE", Delete.class);

    String methodName;

    Class methodClass;

    RequestMethod(String methodName, Class methodClass){
        this.methodName = methodName;
        this.methodClass = methodClass;
    }

    public final String getMethodName(){
        return methodName;
    }

    public final Class getMethodClass(){
        return methodClass;
    }

}
