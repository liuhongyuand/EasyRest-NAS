package com.easyrest.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.easyrest.actors.remote.RemoteInvokeActor;
import com.easyrest.actors.remote.RemoteServiceExchangeActor;
import com.easyrest.network.NettyInit;

public class ActorFactory {

    private static ActorSystem ACTOR_SYSTEM;

    static {
        ACTOR_SYSTEM = ActorSystem.create(NettyInit.SystemName);
        createActorWithName(RemoteInvokeActor.class);
        createActorWithName(RemoteServiceExchangeActor.class);
    }

    public static ActorSystem getActorSystem() {
        return ACTOR_SYSTEM;
    }

    public static ActorRef createActor(Class target){
        return ACTOR_SYSTEM.actorOf(Props.create(target));
    }

    private static void createActorWithName(Class target){
        ACTOR_SYSTEM.actorOf(Props.create(target), target.getSimpleName());
    }

    public static ActorRef createRemoteServiceExchangedActor(String systemName, String host, String port){
        return ACTOR_SYSTEM.actorFor(String.format("akka.tcp://%s@%s:%s/user/RemoteServiceExchangeActor", systemName, host, port));
    }

    public static ActorRef createRemoteInvokeActor(String systemName, String host, String port){
        return ACTOR_SYSTEM.actorFor(String.format("akka.tcp://%s@%s:%s/user/RemoteInvokeActor", systemName, host, port));
    }

}
