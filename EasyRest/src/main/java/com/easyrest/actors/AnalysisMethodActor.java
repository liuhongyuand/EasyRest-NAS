package com.easyrest.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.easyrest.EasyRest;
import com.easyrest.actors.remote.conf.EasyRestDistributedServiceBind;
import com.easyrest.annotations.bean.BindController;
import com.easyrest.annotations.method.*;
import com.easyrest.network.NettyLaunch;
import com.easyrest.network.exception.ConfigurationException;
import com.easyrest.network.router.RouterProvider;
import com.easyrest.utils.LogUtils;
import io.netty.handler.codec.http.HttpMethod;

import java.lang.reflect.Method;

public class AnalysisMethodActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(EasyRest.class, (this::analysisRestObject)).build();
    }

    private void analysisRestObject(EasyRest easyRest) {
        for (Class requestModel : easyRest.getRequestModels()) {
            if (!requestModel.isInterface()) {
                throw new ConfigurationException("Only interface can be registered.");
            }
            if (!requestModel.isAnnotationPresent(BindController.class)) {
                LogUtils.error(requestModel.getSimpleName() + " controller is missing", new ConfigurationException(requestModel.getSimpleName() + " controller is missing"));
                continue;
            }
            Class controller = ((BindController) requestModel.getAnnotation(BindController.class)).value();
            StringBuffer[] restUri = new StringBuffer[1];
            if (requestModel.isAnnotationPresent(BindURL.class)) {
                String[] uris = ((BindURL) requestModel.getAnnotation(BindURL.class)).value();
                restUri = new StringBuffer[uris.length];
                for (int i = 0; i < restUri.length; i++) {
                    restUri[i] = new StringBuffer(uris[i]);
                }
            }
            for (Method method : requestModel.getMethods()) {
                if (method.isAnnotationPresent(Post.class)) {
                    RouterProvider.methodRouterResolve(restUri, method.getName(), method.getAnnotation(Post.class).value(), HttpMethod.POST, method, controller);
                }
                if (method.isAnnotationPresent(Get.class)) {
                    RouterProvider.methodRouterResolve(restUri, method.getName(), method.getAnnotation(Get.class).value(), HttpMethod.GET, method, controller);
                }
                if (method.isAnnotationPresent(Put.class)) {
                    RouterProvider.methodRouterResolve(restUri, method.getName(), method.getAnnotation(Put.class).value(), HttpMethod.PUT, method, controller);
                }
                if (method.isAnnotationPresent(Delete.class)) {
                    RouterProvider.methodRouterResolve(restUri, method.getName(), method.getAnnotation(Delete.class).value(), HttpMethod.DELETE, method, controller);
                }
            }
            EasyRestDistributedServiceBind.addService(requestModel);
        }
        ActorFactory.createActor(NettyLaunch.class).tell(easyRest, ActorRef.noSender());
    }

}
