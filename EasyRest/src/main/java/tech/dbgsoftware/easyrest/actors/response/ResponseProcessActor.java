package tech.dbgsoftware.easyrest.actors.response;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import tech.dbgsoftware.easyrest.actors.ActorFactory;
import tech.dbgsoftware.easyrest.actors.ExceptionHandleActor;
import tech.dbgsoftware.easyrest.actors.remote.model.RemoteInvokeObject;
import tech.dbgsoftware.easyrest.aop.StaticAopStepUtil;
import tech.dbgsoftware.easyrest.model.HttpEntity;
import tech.dbgsoftware.easyrest.utils.LogUtils;

public class ResponseProcessActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(HttpEntity.class, (httpEntity -> {
            HttpEntity[] httpEntityTemp = {httpEntity};
            StaticAopStepUtil.getAopPostCommitStepList().forEach((step) -> {
                if (httpEntityTemp[0].getErrorMap().size() == 0) {
                    try {
                        httpEntityTemp[0] = step.executeStep(httpEntityTemp[0]);
                    } catch (Exception e) {
                        LogUtils.error(e.getMessage(), e);
                        httpEntityTemp[0].addError(e);
                    }
                }
            });
            if (httpEntityTemp[0].getErrorMap().size() == 0) {
                ActorFactory.createActor(OutputActor.class).tell(httpEntityTemp[0], ActorRef.noSender());
            } else {
                ActorFactory.createActor(ExceptionHandleActor.class).tell(httpEntityTemp[0], ActorRef.noSender());
            }
        })).match(RemoteInvokeObject.class, (remoteInvokeObject -> ActorFactory.createActor(OutputActor.class).tell(remoteInvokeObject, ActorRef.noSender()))).build();
    }

}
