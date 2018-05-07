package tech.dbgsoftware.easyrest.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import tech.dbgsoftware.easyrest.actors.remote.RemoteInvokeActor;
import tech.dbgsoftware.easyrest.actors.remote.RemoteServiceExchangeActor;
import tech.dbgsoftware.easyrest.network.NettyInit;
import tech.dbgsoftware.easyrest.utils.LogUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ActorFactory {

    private static ActorSystem ACTOR_SYSTEM;

    private static final Map<Class, Props> PROPS_CACHE = new ConcurrentHashMap<>();

    private static final Lock LOCK = new ReentrantLock();

    static {
        ACTOR_SYSTEM = ActorSystem.create(NettyInit.SystemName);
        createActorWithName(RemoteInvokeActor.class);
        createActorWithName(RemoteServiceExchangeActor.class);
    }

    public static ActorSystem getActorSystem() {
        return ACTOR_SYSTEM;
    }

    public static ActorRef createActor(Class target){
        try {
            if (!PROPS_CACHE.containsKey(target) && LOCK.tryLock(1, TimeUnit.MINUTES)){
                try {
                    Props props = Props.create(target);
                    PROPS_CACHE.putIfAbsent(target, props);
                } finally {
                    LOCK.unlock();
                }
            }
            return ACTOR_SYSTEM.actorOf(PROPS_CACHE.get(target));
        } catch (InterruptedException e) {
            LogUtils.error(e.getMessage(), e);
            return ACTOR_SYSTEM.actorOf(Props.create(target));
        }
    }

    private static void createActorWithName(Class target){
        try {
            if (!PROPS_CACHE.containsKey(target) && LOCK.tryLock(1, TimeUnit.MINUTES)){
                try {
                    Props props = Props.create(target);
                    PROPS_CACHE.putIfAbsent(target, props);
                } finally {
                    LOCK.unlock();
                }
            }
            ACTOR_SYSTEM.actorOf(PROPS_CACHE.get(target), target.getSimpleName());
        } catch (InterruptedException e) {
            LogUtils.error(e.getMessage(), e);
        }
    }

    public static ActorRef createRemoteServiceExchangedActor(String systemName, String host, String port){
        return ACTOR_SYSTEM.actorFor(String.format("akka.tcp://%s@%s:%s/user/RemoteServiceExchangeActor", systemName, host, port));
    }

    public static ActorRef createRemoteInvokeActor(String systemName, String host, String port){
        return ACTOR_SYSTEM.actorFor(String.format("akka.tcp://%s@%s:%s/user/RemoteInvokeActor", systemName, host, port));
    }

}
