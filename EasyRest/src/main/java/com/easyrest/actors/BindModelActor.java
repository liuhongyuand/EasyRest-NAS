package com.easyrest.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.easyrest.EasyRest;
import com.easyrest.annotations.bean.BindController;
import com.easyrest.annotations.method.*;
import com.easyrest.model.request.RestObject;
import com.easyrest.network.NettyLaunch;
import com.easyrest.network.exception.ConfigurationException;
import com.easyrest.network.router.RouterProvider;
import com.easyrest.utils.LogUtils;
import io.netty.handler.codec.http.HttpMethod;

import java.lang.reflect.Method;

public class BindModelActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(EasyRest.class, (this::analysisRestObject)).build();
    }

    private void analysisRestObject(EasyRest easyRest){
        for (Class requestModel: easyRest.getRequestModels()) {
            if (!requestModel.isInterface()){
                throw new ConfigurationException("Only interface can be registered.");
            }
            if (!requestModel.isAnnotationPresent(BindController.class)){
                LogUtils.error(requestModel.getSimpleName() + " controller is missing", new ConfigurationException(requestModel.getSimpleName() + " controller is missing"));
                System.exit(-1);
            }
            Class controller = ((BindController)requestModel.getAnnotation(BindController.class)).value();
            StringBuffer[] restUri = new StringBuffer[1];
            if (requestModel.isAnnotationPresent(BindURL.class)){
                String[] uris = ((BindURL)requestModel.getAnnotation(BindURL.class)).value();
                restUri = new StringBuffer[uris.length];
                for (int i = 0; i < restUri.length; i++) {
                    restUri[i] = new StringBuffer(uris[i]);
                }
            }
            for (Method method : requestModel.getMethods()) {
                RestObject restObject;
                if (method.isAnnotationPresent(Post.class)) {
                    String url = method.getName();
                    restObject = new RestObject(method, HttpMethod.POST, controller);
                    for (StringBuffer aRestUri : restUri) {
                        putRouter(aRestUri.toString(), url, HttpMethod.POST, restObject);
                    }
                }
                if (method.isAnnotationPresent(Get.class)) {
                    String url = method.getName();
                    restObject = new RestObject(method, HttpMethod.GET, controller);
                    for (StringBuffer aRestUri : restUri) {
                        putRouter(aRestUri.toString(), url, HttpMethod.GET, restObject);
                    }
                }
                if (method.isAnnotationPresent(Put.class)) {
                    String url = method.getName();
                    restObject = new RestObject(method, HttpMethod.PUT, controller);
                    for (StringBuffer aRestUri : restUri) {
                        putRouter(aRestUri.toString(), url, HttpMethod.PUT, restObject);
                    }
                }
                if (method.isAnnotationPresent(Delete.class)) {
                    String url = method.getName();
                    restObject = new RestObject(method, HttpMethod.DELETE, controller);
                    for (StringBuffer aRestUri : restUri) {
                        putRouter(aRestUri.toString(), url, HttpMethod.DELETE, restObject);
                    }
                }
            }
        }
        ActorFactory.createActor(NettyLaunch.class).tell(easyRest, ActorRef.noSender());
    }

    private void putRouter(String bindUrl, String methodUrl, HttpMethod httpMethod, RestObject restObject){
        StringBuilder url = new StringBuilder(bindUrl);
        if (!bindUrl.startsWith("/")){
            url.insert(0, "/");
        }
        if (!bindUrl.endsWith("/") && !methodUrl.startsWith("/")){
            url.insert(bindUrl.length(), "/");
        }
        url.append(methodUrl);
        if (RouterProvider.getRestObjectMap().containsKey(url.toString())){
            RouterProvider.getRestObjectMap().get(url.toString()).addHttpMethod(httpMethod);
        } else {
            RouterProvider.getRestObjectMap().put(url.toString(), restObject);
        }
    }

}
