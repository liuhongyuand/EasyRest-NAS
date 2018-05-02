package tech.dbgsoftware.easyrest.actors.remote;

import akka.pattern.Patterns;
import akka.util.Timeout;
import tech.dbgsoftware.easyrest.actors.ActorFactory;
import tech.dbgsoftware.easyrest.actors.remote.conf.EasyRestDistributedServiceBind;
import tech.dbgsoftware.easyrest.actors.remote.model.RemoteInvokeObject;
import tech.dbgsoftware.easyrest.actors.remote.model.ServiceInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.lang.reflect.Method;

public class RemoteRequestUtil {

    private static final Gson GSON = new GsonBuilder().create();

    private static final Timeout REQUEST_TIMEOUT = new Timeout(Duration.create(60, "seconds"));

    private static final Timeout REQUEST_TIMEOUT_FOR_INIT = new Timeout(Duration.create(5, "seconds"));

    public static Object createRemoteRequest(Method method, Object[] args){
        ServiceInfo serviceInfo = EasyRestDistributedServiceBind.getServiceInfoMap().get(method.getDeclaringClass().getName());
        RemoteInvokeObject remoteInvokeObject = new RemoteInvokeObject(method, args);
        return getInvoke(serviceInfo.getAkkaSystemName(), serviceInfo.getHost(), serviceInfo.getPort(), GSON.toJson(remoteInvokeObject));
    }

    private static Object getInvoke(String remoteActorSystemName, String remoteHost, String port, Object msg){
        try {
            return Await.result(Patterns.ask(ActorFactory.createRemoteInvokeActor(remoteActorSystemName, remoteHost, port), msg, REQUEST_TIMEOUT), REQUEST_TIMEOUT.duration());
        } catch (Exception e) {
            return null;
        }
    }

    static Object getServiceExchanged(String remoteActorSystemName, String remoteHost, String port, Object msg){
        try {
            return Await.result(Patterns.ask(ActorFactory.createRemoteServiceExchangedActor(remoteActorSystemName, remoteHost, port), msg, REQUEST_TIMEOUT_FOR_INIT), REQUEST_TIMEOUT_FOR_INIT.duration());
        } catch (Exception e) {
            return null;
        }
    }

}
