package tech.dbgsoftware.easyrest.actors.request;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import tech.dbgsoftware.easyrest.actors.ActorFactory;
import tech.dbgsoftware.easyrest.actors.ExceptionHandleActor;
import tech.dbgsoftware.easyrest.actors.response.OutputActor;
import tech.dbgsoftware.easyrest.aop.StaticAopStepUtil;
import tech.dbgsoftware.easyrest.exception.PageNotFoundException;
import tech.dbgsoftware.easyrest.model.HttpEntity;
import tech.dbgsoftware.easyrest.utils.LogUtils;

public class RequestProcessActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(HttpEntity.class, (httpEntity -> {
            HttpEntity[] httpEntityTemp = {httpEntity};
            StaticAopStepUtil.getAopPreCommitStepList().forEach((step) -> {
                if (httpEntityTemp[0].getErrorMap().size() == 0 && !httpEntityTemp[0].isOptionsCheck()) {
                    try {
                        httpEntityTemp[0] = step.executeStep(httpEntityTemp[0]);
                    } catch (Exception e) {
                        httpEntityTemp[0].addError(e);
                        if (e instanceof PageNotFoundException) {
                            httpEntity.getChannelHandlerContext().close();
                            return;
                        }
                        LogUtils.error(e.getMessage(), e);
                    }
                }
            });
            if (httpEntityTemp[0].getErrorMap().size() == 0) {
                if (httpEntityTemp[0].isOptionsCheck()) {
                    ActorFactory.createActor(OutputActor.class).tell(httpEntityTemp[0], ActorRef.noSender());
                } else {
                    ActorFactory.createActor(ControllerInvokeActor.class).tell(httpEntityTemp[0], ActorRef.noSender());
                }
            } else {
                ActorFactory.createActor(ExceptionHandleActor.class).tell(httpEntityTemp[0], ActorRef.noSender());
            }
        })).build();
    }

}
