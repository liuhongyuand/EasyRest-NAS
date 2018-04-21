package com.easyrest.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ActorFactory {

    private static final ActorSystem ACTOR_SYSTEM = ActorSystem.create("ACTOR_SYSTEM");

    public static ActorRef createActor(Class target){
        return ACTOR_SYSTEM.actorOf(Props.create(target));
    }

}
