package com.easyrest.aop;

public class AopInvokeStep implements AopStep {
    @Override
    public <T> T executeStep(T t) {
        return null;
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
