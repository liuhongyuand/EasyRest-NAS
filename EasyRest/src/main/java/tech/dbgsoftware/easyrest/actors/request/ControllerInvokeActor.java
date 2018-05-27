package tech.dbgsoftware.easyrest.actors.request;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import tech.dbgsoftware.easyrest.actors.ActorFactory;
import tech.dbgsoftware.easyrest.actors.ExceptionHandleActor;
import tech.dbgsoftware.easyrest.actors.Signal;
import tech.dbgsoftware.easyrest.actors.remote.model.RemoteInvokeObject;
import tech.dbgsoftware.easyrest.actors.response.ResponseProcessActor;
import tech.dbgsoftware.easyrest.ioc.utils.BeanOperationUtils;
import tech.dbgsoftware.easyrest.model.HttpEntity;
import tech.dbgsoftware.easyrest.model.ResponseEntity;
import tech.dbgsoftware.easyrest.utils.LogUtils;

import java.lang.reflect.Method;

public class ControllerInvokeActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(HttpEntity.class, (httpEntity -> {
            try {
                Method method = httpEntity.getMethod();
                Class<?> controller = httpEntity.getController();
                if (method.getReturnType().getName().equalsIgnoreCase(Void.class.getSimpleName())) {
                    invokeMethod(method, controller, httpEntity.getArgs(), null);
                    httpEntity.setResponseEntity(ResponseEntity.buildOkResponse());
                } else if (method.getReturnType().equals(ResponseEntity.class)) {
                    httpEntity.setResponseEntity((ResponseEntity) invokeMethod(method, controller, httpEntity.getArgs(), null));
                } else {
                    httpEntity.setResponseEntity(ResponseEntity.buildBaseResponse(invokeMethod(method, controller, httpEntity.getArgs(), null)));
                }
                ActorFactory.createActor(ResponseProcessActor.class).tell(httpEntity, ActorRef.noSender());
            } catch (Exception e) {
                LogUtils.error(e.getMessage(), e);
                httpEntity.addError(e);
                ActorFactory.createActor(ExceptionHandleActor.class).tell(httpEntity, ActorRef.noSender());
            }
        })).match(RemoteInvokeObject.class, (remoteInvokeObject -> {
            try {
                Object result = invokeMethod(remoteInvokeObject.getMethod(), remoteInvokeObject.getImplClass(), remoteInvokeObject.getArgs(), remoteInvokeObject.getInvokeBeanName());
                if (remoteInvokeObject.getMethod().getReturnType().getName().equalsIgnoreCase(Void.class.getSimpleName())) {
                    remoteInvokeObject.setResult(ResponseEntity.buildOkResponse());
                } else {
                    remoteInvokeObject.setResult(result);
                }
                ActorFactory.createActor(ResponseProcessActor.class).tell(remoteInvokeObject, ActorRef.noSender());
            } catch (Exception e) {
                LogUtils.error(e.getMessage(), e);
                remoteInvokeObject.getSender().tell(Signal.getFailedMessage(e.getMessage()), remoteInvokeObject.getSender());
            }
        })).build();
    }

    private Object invokeMethod(Method method, Class<?> controller, Object[] args, String invokeBeanName) throws Exception {
        if (args.length > 0) {
            if (invokeBeanName == null) {
                return method.invoke(BeanOperationUtils.getBean(controller), args);
            } else {
                return method.invoke(BeanOperationUtils.getBean(invokeBeanName, controller), args);
            }
        } else {
            if (invokeBeanName == null) {
                return method.invoke(BeanOperationUtils.getBean(controller));
            } else {
                return method.invoke(BeanOperationUtils.getBean(invokeBeanName, controller));
            }
        }
    }

}
