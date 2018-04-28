package com.easyrest.actors.remote;

import akka.actor.AbstractActor;

public class RemoteInvokeActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny((object) -> {
            getSender().tell(object, getSender());
        }).build();
    }
}
