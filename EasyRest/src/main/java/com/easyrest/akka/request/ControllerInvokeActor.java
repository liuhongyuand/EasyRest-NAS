package com.easyrest.akka.request;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.easyrest.akka.ActorFactory;
import com.easyrest.akka.response.ResponseProcessActor;
import com.easyrest.ioc.utils.BeanOperationUtils;
import com.easyrest.model.HttpEntity;
import com.easyrest.model.ResponseEntity;
import com.easyrest.model.request.RestObject;
import com.easyrest.router.RouterProvider;
import com.sun.org.apache.xpath.internal.Arg;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ControllerInvokeActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(HttpEntity.class, (httpEntity -> {
            RestObject restObject = RouterProvider.getRestObjectMap().get(httpEntity.getRequest().getRequestUri());
            Method method = restObject.getMethod();
            Class controller = restObject.getController();
            Arg[] args = new Arg[method.getParameters().length];
            for (int i = 0; i < args.length; i++) {
                args[i] = null;
            }
            try {
                ResponseEntity responseEntity = (ResponseEntity) method.invoke(BeanOperationUtils.getBean(controller), args);
                httpEntity.setResponseEntity(responseEntity);
                ActorFactory.createActor(ResponseProcessActor.class).tell(httpEntity, ActorRef.noSender());
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        })).build();
    }

}
