package com.easyrest.actors.response;

import akka.actor.AbstractActor;
import com.easyrest.model.HttpEntity;
import com.easyrest.model.ResponseEntity;

public class OutputActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(HttpEntity.class, (httpEntity -> {
            if (httpEntity.getRestObject().getMethod().getReturnType().equals(ResponseEntity.class) ||
                    httpEntity.getRestObject().getMethod().getReturnType().getSimpleName().equalsIgnoreCase(Void.class.getSimpleName())) {
                httpEntity.getResponse().buildResponse(httpEntity.getResponseEntity());
            } else {
                httpEntity.getResponse().buildResponse(httpEntity.getResponseEntity().getData());
            }
            httpEntity.getChannelHandlerContext().writeAndFlush(httpEntity.getResponse().getRealResponse());
        })).build();
    }
}
