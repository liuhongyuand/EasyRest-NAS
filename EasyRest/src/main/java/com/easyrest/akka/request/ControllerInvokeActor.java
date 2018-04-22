package com.easyrest.akka.request;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.easyrest.akka.ActorFactory;
import com.easyrest.akka.ExceptionHandleActor;
import com.easyrest.akka.response.ResponseProcessActor;
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
                Object[] args = httpEntity.getArgs();
                ResponseEntity responseEntity;
                if (args.length > 0){
                    responseEntity = (ResponseEntity) method.invoke(BeanOperationUtils.getBean(controller), args);
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
