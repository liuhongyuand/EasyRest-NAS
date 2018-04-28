package com.easyrest.actors.remote;

import akka.pattern.Patterns;
import akka.util.Timeout;
import com.easyrest.actors.ActorFactory;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

public class RemoteRequestUtil {

    private static final Timeout REQUEST_TIMEOUT = new Timeout(Duration.create(10, "seconds"));

    public static Object getInvoke(String remoteActorSystemName, String remoteHost, String port, Object msg){
        try {
            return Await.result(Patterns.ask(ActorFactory.createRemoteInvokeActor(remoteActorSystemName, remoteHost, port), msg, REQUEST_TIMEOUT), REQUEST_TIMEOUT.duration());
        } catch (Exception e) {
            return null;
        }
    }

    public static Object getServiceExchanged(String remoteActorSystemName, String remoteHost, String port, Object msg){
        try {
            return Await.result(Patterns.ask(ActorFactory.createRemoteServiceExchangedActor(remoteActorSystemName, remoteHost, port), msg, REQUEST_TIMEOUT), REQUEST_TIMEOUT.duration());
        } catch (Exception e) {
            return null;
        }
    }

}
