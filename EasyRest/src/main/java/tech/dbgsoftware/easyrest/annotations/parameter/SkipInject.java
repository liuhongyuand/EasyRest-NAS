package tech.dbgsoftware.easyrest.annotations.parameter;

import java.lang.annotation.*;

/**
 * Created by ${Lhy} on 17/2/4.
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SkipInject {
}
