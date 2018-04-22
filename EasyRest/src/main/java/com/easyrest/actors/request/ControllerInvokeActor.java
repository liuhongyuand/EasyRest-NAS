package com.easyrest.actors.request;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.easyrest.actors.ActorFactory;
import com.easyrest.actors.ExceptionHandleActor;
import com.easyrest.actors.response.ResponseProcessActor;
import com.easyrest.ioc.utils.BeanOperationUtils;
import com.easyrest.model.HttpEntity;
import com.easyrest.model.ResponseEntity;

import java.lang.reflect.Method;

public class ControllerInvokeActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(HttpEntity.class, (httpEntity -> {
            try {
                Method method = httpEntity.getMethod();
                Class<?> controller = httpEntity.getController();
                ResponseEntity responseEntity;
                if (httpEntity.getArgs().length > 0){
                    responseEntity = (ResponseEntity) method.invoke(BeanOperationUtils.getBean(controller), httpEntity.getArgs());
                } else {
                    responseEntity = (ResponseEntity) method.invoke(BeanOperationUtils.getBean(controller));
                }
                httpEntity.setResponseEntity(responseEntity);
                ActorFactory.createActor(ResponseProcessActor.class).tell(httpEntity, ActorRef.noSender());
            } catch (Exception e) {
                e.printStackTrace();
                httpEntity.addError(e);
                ActorFactory.createActor(ExceptionHandleActor.class).tell(httpEntity, ActorRef.noSender());
            }
        })).build();
    }

}
