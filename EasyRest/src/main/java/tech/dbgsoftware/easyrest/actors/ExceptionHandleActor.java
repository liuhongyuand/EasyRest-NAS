package tech.dbgsoftware.easyrest.actors;

import akka.actor.AbstractActor;
import tech.dbgsoftware.easyrest.model.ErrorResponse;
import tech.dbgsoftware.easyrest.model.HttpEntity;
import tech.dbgsoftware.easyrest.model.ResponseEntity;

public class ExceptionHandleActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(HttpEntity.class, (httpEntity -> {
                    httpEntity.getResponse().buildResponse(ResponseEntity.buildFailedResponse(new ErrorResponse(httpEntity.getErrorMap())));
                    httpEntity.getChannelHandlerContext().writeAndFlush(httpEntity.getResponse().getRealResponse());
                })).build();
    }

}
