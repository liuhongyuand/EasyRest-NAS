package com.easyrest.akka.request;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.easyrest.akka.ActorFactory;
import com.easyrest.akka.ExceptionHandleActor;
import com.easyrest.aop.StaticAopStepUtil;
import com.easyrest.model.HttpEntity;

public class RequestProcessActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(HttpEntity.class, (httpEntity -> {
            HttpEntity[] httpEntityTemp = {httpEntity};
            StaticAopStepUtil.getAopPreCommitStepList().forEach((step) -> {
                if (httpEntityTemp[0].getErrorMap().size() == 0) {
                    try {
                        httpEntityTemp[0] = step.executeStep(httpEntityTemp[0]);
                    } catch (Exception e) {
                        httpEntityTemp[0].addError(e);
                    }
                }
            });
            if (httpEntityTemp[0].getErrorMap().size() == 0) {
                ActorFactory.createActor(ControllerInvokeActor.class).tell(httpEntityTemp[0], ActorRef.noSender());
            } else {
                ActorFactory.createActor(ExceptionHandleActor.class).tell(httpEntityTemp[0], ActorRef.noSender());
            }
        })).build();
    }

}
