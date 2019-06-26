package tech.dbgsoftware.easyrest.annotations.method;

import java.lang.annotation.*;

/**
 * Created by honliu on 2019/6/26.
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SkipCustomerInject {
}
