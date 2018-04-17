package com.easyrest.annotations.history;


import com.easyrest.annotations.transaction.TransactionRequired;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liuhongyu.louie on 2017/1/9.
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@TransactionRequired
public @interface HistoryRequired {
    String[] value() default {};
}
