package tech.dbgsoftware.easyrest.actors.response;

import akka.actor.AbstractActor;
import tech.dbgsoftware.easyrest.actors.remote.model.RemoteInvokeObject;
import tech.dbgsoftware.easyrest.model.HttpEntity;
import tech.dbgsoftware.easyrest.model.ResponseEntity;
import tech.dbgsoftware.easyrest.utils.JsonTranslationUtil;

public class OutputActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(HttpEntity.class, (httpEntity -> {
            if (httpEntity.isOptionsCheck() ||
                    httpEntity.getRestObject().getMethod().getReturnType().equals(ResponseEntity.class) ||
                    httpEntity.getRestObject().getMethod().getReturnType().getSimpleName().equalsIgnoreCase(Void.class.getSimpleName())) {
                httpEntity.getResponse().buildResponse(httpEntity.getResponseEntity());
            } else if (httpEntity.getRestObject().getMethod().getReturnType().equals(String.class)) {
                httpEntity.getResponse().buildStringResponse(String.valueOf(httpEntity.getResponseEntity().getData()));
            } else {
                httpEntity.getResponse().buildResponse(httpEntity.getResponseEntity().getData());
            }
            httpEntity.getChannelHandlerContext().writeAndFlush(httpEntity.getResponse().getRealResponse());
        })).match(RemoteInvokeObject.class, (remoteInvokeObject -> remoteInvokeObject.getSender().tell(JsonTranslationUtil.toJsonString(remoteInvokeObject.getResult()), remoteInvokeObject.getSender()))).build();
    }
}
