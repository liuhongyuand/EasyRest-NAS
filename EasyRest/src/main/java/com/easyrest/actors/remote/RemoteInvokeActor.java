package com.easyrest.actors.remote;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.easyrest.actors.ActorFactory;
import com.easyrest.actors.remote.model.RemoteInvokeObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RemoteInvokeActor extends AbstractActor {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(String.class, (jsonData)-> {
            RemoteInvokeObject remoteInvokeObject = GSON.fromJson(jsonData, RemoteInvokeObject.class);
            remoteInvokeObject.setSender(getSender());
            ActorFactory.createActor(RemoteObjectAnalysisActor.class).tell(remoteInvokeObject, ActorRef.noSender());
        }).build();
    }
}
