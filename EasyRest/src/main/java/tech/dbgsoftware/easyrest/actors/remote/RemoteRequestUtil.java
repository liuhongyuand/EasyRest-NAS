package tech.dbgsoftware.easyrest.actors.remote;

import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import tech.dbgsoftware.easyrest.actors.ActorFactory;
import tech.dbgsoftware.easyrest.actors.remote.conf.EasyRestDistributedServiceBind;
import tech.dbgsoftware.easyrest.actors.remote.model.RemoteInvokeObject;
import tech.dbgsoftware.easyrest.actors.remote.model.ServiceInfo;
import tech.dbgsoftware.easyrest.utils.JsonTranslationUtil;
import tech.dbgsoftware.easyrest.utils.LogUtils;

import java.lang.reflect.Method;

public class RemoteRequestUtil {

    private static final Timeout REQUEST_TIMEOUT = new Timeout(Duration.create(60, "seconds"));

    private static final Timeout REQUEST_TIMEOUT_FOR_INIT = new Timeout(Duration.create(10, "seconds"));

    public static Object createRemoteRequest(Method method, Object[] args, long connectionMillis, long resultMillis){
        return createRemoteRequest(method, args, null, connectionMillis, resultMillis);
    }

    public static Object createRemoteRequest(Method method, Object[] args, String invokeBeanName, long connectionMillis, long resultMillis){
        ServiceInfo serviceInfo = EasyRestDistributedServiceBind.getServiceInfoMap().get(method.getDeclaringClass().getName());
        RemoteInvokeObject remoteInvokeObject;
        if (invokeBeanName != null) {
            remoteInvokeObject = new RemoteInvokeObject(method, args, invokeBeanName);
        } else {
            remoteInvokeObject = new RemoteInvokeObject(method, args);
        }
        return getInvoke(serviceInfo.getAkkaSystemName(), serviceInfo.getHost(), serviceInfo.getPort(), JsonTranslationUtil.toJsonString(remoteInvokeObject), connectionMillis, resultMillis);
    }

    private static Object getInvoke(String remoteActorSystemName, String remoteHost, String port, Object msg, long connectionMillis, long resultMillis){
        try {
            if (connectionMillis > 0 && resultMillis > 0){
                return Await.result(getInvokeFuture(remoteActorSystemName, remoteHost, port, msg, new Timeout(Duration.create(connectionMillis, "millis"))), new Timeout(Duration.create(resultMillis, "millis")).duration());
            } else {
                return Await.result(getInvokeFuture(remoteActorSystemName, remoteHost, port, msg, REQUEST_TIMEOUT), REQUEST_TIMEOUT.duration());
            }
        } catch (Exception e) {
            LogUtils.error(e.getMessage(), e);
            return null;
        }
    }

    private static Future<Object> getInvokeFuture(String remoteActorSystemName, String remoteHost, String port, Object msg, Timeout timeout){
        try {
            return Patterns.ask(ActorFactory.createRemoteInvokeActor(remoteActorSystemName, remoteHost, port), msg, timeout);
        } catch (Exception e) {
            LogUtils.error(e.getMessage(), e);
            return null;
        }
    }

    static Object getServiceExchanged(String remoteActorSystemName, String remoteHost, String port, Object msg){
        try {
            return Await.result(Patterns.ask(ActorFactory.createRemoteServiceExchangedActor(remoteActorSystemName, remoteHost, port), msg, REQUEST_TIMEOUT_FOR_INIT), REQUEST_TIMEOUT_FOR_INIT.duration());
        } catch (Exception e) {
            LogUtils.error(e.getMessage(), e);
            return null;
        }
    }

}
