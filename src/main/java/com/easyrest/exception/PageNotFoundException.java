package com.easyrest.exception;

/**
 * Page not found exception.
 * Created by liuhongyu.louie on 2016/10/1.
 */
public class PageNotFoundException extends EasyRestException {

    public PageNotFoundException(String message) {
        super(message);
    }

    public static PageNotFoundException getException(String message){
        return new PageNotFoundException(message);
    }

}
