package com.easyrest.actors.remote.model;

import akka.actor.ActorRef;

import java.lang.reflect.Method;

public class RemoteInvokeObject {

    private ActorRef sender;

    private Class implClass;

    private Method method;

    private String interfaceClassName;

    private String methodName;

    private Object[] args;

    private Object result;

    public RemoteInvokeObject(Method method, Object[] args) {
        this.interfaceClassName = method.getDeclaringClass().getName();
        this.methodName = method.getName();
        this.args = args;
    }

    public String getInterfaceClassName() {
        return interfaceClassName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void updateArgs(Object[] args) {
        this.args = args;
    }

    public Object[] getArgs() {
        return args;
    }

    public ActorRef getSender() {
        return sender;
    }

    public Class getImplClass() {
        return implClass;
    }

    public void setImplClass(Class implClass) {
        this.implClass = implClass;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setSender(ActorRef sender) {
        this.sender = sender;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
