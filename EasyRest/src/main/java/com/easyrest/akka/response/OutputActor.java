package com.easyrest.akka.response;

import akka.actor.AbstractActor;
import com.easyrest.model.HttpEntity;

public class OutputActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(HttpEntity.class, (httpEntity -> {
            httpEntity.getResponse().buildResponse(httpEntity.getResponseEntity());
            httpEntity.getChannelHandlerContext().writeAndFlush(httpEntity.getResponse().getRealResponse());
        })).build();
    }
}
