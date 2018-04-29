package com.easyrest.ioc.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Class> getAllBeansClass(){
        List<Class> beans = new ArrayList<>();
        for (String name: defaultListableBeanFactory.getBeanDefinitionNames()){
            beans.add(defaultListableBeanFactory.getBean(name).getClass());
        }
        return beans;
    }

    public static <T> T getBeansFromInterface(Class<T> _interface){
        return getBeansFromInterface(_interface, null);
    }

    public static <T> T getBeansFromInterface(Class<T> _interface, String name){
        try {
            if (name == null){
                final List<T> firstBean = new ArrayList<>();
                defaultListableBeanFactory.getBeansOfType(_interface).forEach((beanName, bean) -> {
                    if (firstBean.size() == 0) {
                        firstBean.add(bean);
                    }
                });
                if (firstBean.size() > 0){
                    return firstBean.get(0);
                } else {
                    return null;
                }
            } else {
                return defaultListableBeanFactory.getBeansOfType(_interface).get(name);
            }
        } catch (Exception e){
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
