package com.easyrest.aop;

import com.easyrest.model.HttpEntity;

public class AopInvokeStep implements AopStep {

    @Override
    public HttpEntity executeStep(HttpEntity entity) {
        return entity;
    }

    public void invoke(){
//        Class impl = method.getAnnotation(BindController.class).value();
//        Arg[] args = new Arg[method.getParameters().length];
//        for (int i = 0; i < args.length; i++) {
//            args[i] = null;
//        }
//        try {
//            method.invoke(BeanOperationUtils.getBean(impl), args);
//        } catch (IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
    }
}
