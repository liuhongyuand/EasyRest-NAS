package com.easyrest.actors.request;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.easyrest.actors.ActorFactory;
import com.easyrest.actors.ExceptionHandleActor;
import com.easyrest.actors.Signal;
import com.easyrest.actors.remote.model.RemoteInvokeObject;
import com.easyrest.actors.response.ResponseProcessActor;
import com.easyrest.ioc.utils.BeanOperationUtils;
import com.easyrest.model.HttpEntity;
import com.easyrest.model.ResponseEntity;
import com.easyrest.utils.LogUtils;

import java.lang.reflect.Method;

public class ControllerInvokeActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(HttpEntity.class, (httpEntity -> {
            try {
                Method method = httpEntity.getMethod();
                Class<?> controller = httpEntity.getController();
                if (method.getReturnType().getName().equalsIgnoreCase(Void.class.getSimpleName())){
                    invokeMethod(method, controller, httpEntity.getArgs());
                    httpEntity.setResponseEntity(ResponseEntity.buildOkResponse());
                } else if (method.getReturnType().equals(ResponseEntity.class)){
                    httpEntity.setResponseEntity((ResponseEntity) invokeMethod(method, controller, httpEntity.getArgs()));
                } else {
                    httpEntity.setResponseEntity(ResponseEntity.buildBaseResponse(invokeMethod(method, controller, httpEntity.getArgs())));
                }
                ActorFactory.createActor(ResponseProcessActor.class).tell(httpEntity, ActorRef.noSender());
            } catch (Exception e) {
                LogUtils.error(e.getMessage(), e);
                httpEntity.addError(e);
                ActorFactory.createActor(ExceptionHandleActor.class).tell(httpEntity, ActorRef.noSender());
            }
        })).match(RemoteInvokeObject.class, (remoteInvokeObject -> {
            try {
                Object result = invokeMethod(remoteInvokeObject.getMethod(), remoteInvokeObject.getImplClass(), remoteInvokeObject.getArgs());
                if (remoteInvokeObject.getMethod().getReturnType().getName().equalsIgnoreCase(Void.class.getSimpleName())){
                    remoteInvokeObject.setResult(ResponseEntity.buildOkResponse());
                } else {
                    remoteInvokeObject.setResult(result);
                }
                ActorFactory.createActor(ResponseProcessActor.class).tell(remoteInvokeObject, ActorRef.noSender());
            } catch (Exception e){
                LogUtils.error(e.getMessage(), e);
                remoteInvokeObject.getSender().tell(Signal.getFailedMessage(e.getMessage()), remoteInvokeObject.getSender());
            }
        })).build();
    }

    private Object invokeMethod(Method method, Class<?> controller, Object[] args) throws Exception{
            if (args.length > 0) {
                return method.invoke(BeanOperationUtils.getBean(controller), args);
            } else {
                return method.invoke(BeanOperationUtils.getBean(controller));
            }
    }

}
