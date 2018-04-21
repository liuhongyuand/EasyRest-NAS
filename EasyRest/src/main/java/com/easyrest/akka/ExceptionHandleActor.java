package com.easyrest.akka;

import akka.actor.AbstractActor;
import com.easyrest.model.ErrorResponse;
import com.easyrest.model.HttpEntity;
import com.easyrest.model.ResponseEntity;

public class ExceptionHandleActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(HttpEntity.class, (httpEntity -> {
            httpEntity.getResponse().buildResponse(ResponseEntity.buildFailedResponse(new ErrorResponse(httpEntity.getErrorMap())));
            httpEntity.getChannelHandlerContext().writeAndFlush(httpEntity.getResponse().getRealResponse());
        })).build();
    }

}
