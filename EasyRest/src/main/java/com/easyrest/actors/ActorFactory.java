package com.easyrest.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.easyrest.network.NettyInit;

public class ActorFactory {

    private static ActorSystem ACTOR_SYSTEM;

    public static ActorRef createActor(Class target){
        return ACTOR_SYSTEM.actorOf(Props.create(target));
    }

    static {
        ACTOR_SYSTEM = ActorSystem.create(NettyInit.SystemName);
    }

}
