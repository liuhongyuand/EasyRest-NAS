package com.easyrest.annotations.method;

import java.lang.annotation.*;

/**
 * Created by liuhongyu.louie on 2016/10/23.
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Put {
}
