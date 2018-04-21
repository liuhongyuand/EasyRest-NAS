package com.easyrest.akka.response;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.easyrest.akka.ActorFactory;
import com.easyrest.aop.StaticAopStepUtil;
import com.easyrest.model.HttpEntity;

public class ResponseProcessActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(HttpEntity.class, (httpEntity -> {
            HttpEntity[] httpEntityTemp = {httpEntity};
            StaticAopStepUtil.getAopPostCommitStepList().forEach((step) -> httpEntityTemp[0] = step.executeStep(httpEntityTemp[0]));
            ActorFactory.createActor(OutputActor.class).tell(httpEntityTemp[0], ActorRef.noSender());
        })).build();
    }

}
