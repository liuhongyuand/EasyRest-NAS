package com.easyrest.actors.response;

import akka.actor.AbstractActor;
import com.easyrest.actors.remote.model.RemoteInvokeObject;
import com.easyrest.model.HttpEntity;
import com.easyrest.model.ResponseEntity;
import com.easyrest.utils.JsonTranslationUtil;

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
        })).match(RemoteInvokeObject.class, (remoteInvokeObject -> remoteInvokeObject.getSender().tell(JsonTranslationUtil.toJsonString(remoteInvokeObject.getResult()), remoteInvokeObject.getSender()))).build();
    }
}
