package com.easyrest.actors.remote;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.easyrest.actors.ActorFactory;
import com.easyrest.actors.Signal;
import com.easyrest.actors.remote.conf.EasyRestDistributedServiceBind;
import com.easyrest.actors.remote.model.RemoteInvokeObject;
import com.easyrest.actors.request.ControllerInvokeActor;
import com.easyrest.aop.resolvers.JsonDataResolve;

import java.lang.reflect.Method;

public class RemoteObjectAnalysisActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(RemoteInvokeObject.class, (remoteInvokeObject -> {
            Class controller = EasyRestDistributedServiceBind.getLocalServiceControllerMap().get(remoteInvokeObject.getInterfaceClassName());
            remoteInvokeObject.setImplClass(controller);
            Method invokeMethod = null;
            for (Method method : controller.getMethods()) {
                if (method.getName().equals(remoteInvokeObject.getMethodName())){
                    invokeMethod = method;
                    break;
                }
            }
            if (invokeMethod == null){
                remoteInvokeObject.getSender().tell(Signal.getFailedMessage(remoteInvokeObject.getMethodName() + " not found"), remoteInvokeObject.getSender());
            } else {
                if (invokeMethod.getGenericParameterTypes().length != remoteInvokeObject.getArgs().length){
                    remoteInvokeObject.getSender().tell(Signal.getFailedMessage("parameter number not correct"), remoteInvokeObject.getSender());
                }
                remoteInvokeObject.setMethod(invokeMethod);
                remoteInvokeObject.updateArgs(JsonDataResolve.resolveArgs(invokeMethod.getGenericParameterTypes(), remoteInvokeObject.getArgs()));
                ActorFactory.createActor(ControllerInvokeActor.class).tell(remoteInvokeObject, ActorRef.noSender());
            }
        })).build();
    }

}
