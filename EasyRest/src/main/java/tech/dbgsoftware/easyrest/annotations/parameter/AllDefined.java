package tech.dbgsoftware.easyrest.annotations.parameter;

import java.lang.annotation.*;

/**
 * Created by liuhongyu.louie on 2016/10/1.
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AllDefined {
}
