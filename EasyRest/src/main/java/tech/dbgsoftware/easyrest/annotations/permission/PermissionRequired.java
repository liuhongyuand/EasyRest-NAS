package tech.dbgsoftware.easyrest.annotations.permission;

import java.lang.annotation.*;

/**
 * Created by liuhongyu.louie on 2016/10/9.
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionRequired {
    String[] parameters() default {""};
}
