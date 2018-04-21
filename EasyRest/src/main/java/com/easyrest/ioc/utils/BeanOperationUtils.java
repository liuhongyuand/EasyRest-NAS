package com.easyrest.ioc.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;

/**
 * Created by liuhongyu.louie on 2017/4/3.
 */
public class BeanOperationUtils {

    private static BeanDefinition bean;

    private static DefaultListableBeanFactory defaultListableBeanFactory;

    public static <T> T registerBean(String beanName, Class<T> aClass) {
        bean.setBeanClassName(aClass.getName());
        defaultListableBeanFactory.registerBeanDefinition(beanName, bean);
        return getBean(beanName, aClass);
    }

    public static <T> T getBean(String beanName, Class<T> beanClass) {
        try {
            return defaultListableBeanFactory.getBean(beanName, beanClass);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T getBean(Class<T> beanClass) {
        try {
            return defaultListableBeanFactory.getBean(beanClass);
        } catch (Exception e) {
            return null;
        }
    }

    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        bean = new GenericBeanDefinition();
        defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
    }
}
