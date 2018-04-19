package com.easyrest.annotations.parameter;

import java.lang.annotation.*;

/**
 * Created by liuhongyu.louie on 2016/9/30.
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AllDefined
public @interface AutoInject {
}
