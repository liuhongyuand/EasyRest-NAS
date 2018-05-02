package tech.dbgsoftware.easyrest.annotations.method;

import tech.dbgsoftware.easyrest.annotations.bean.EasyRestInterface;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liuhongyu.louie on 2017/4/4.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EasyRestInterface
public @interface BindURL {
    String[] value();
}
