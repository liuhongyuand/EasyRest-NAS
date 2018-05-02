package tech.dbgsoftware.easyrest.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import io.netty.handler.codec.http.HttpMethod;
import tech.dbgsoftware.easyrest.EasyRest;
import tech.dbgsoftware.easyrest.actors.remote.conf.EasyRestDistributedServiceBind;
import tech.dbgsoftware.easyrest.annotations.method.*;
import tech.dbgsoftware.easyrest.ioc.utils.BeanOperationUtils;
import tech.dbgsoftware.easyrest.network.NettyLaunch;
import tech.dbgsoftware.easyrest.network.exception.ConfigurationException;
import tech.dbgsoftware.easyrest.network.router.RouterProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnalysisMethodActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(EasyRest.class, (this::analysisRestObject)).build();
    }

    private void analysisRestObject(EasyRest easyRest) {
        List<Class> easyRestClass = new ArrayList<>();
        BeanOperationUtils.getAllBeansClass().forEach((aClass -> {
            for (Class _interface : aClass.getInterfaces()){
                if (_interface.isAnnotationPresent(BindURL.class)){
                    easyRestClass.add(_interface);
                }
            }
        }));
        for (Class<?> requestModel : easyRestClass) {
            if (!requestModel.isInterface()) {
                throw new ConfigurationException("Only interface can be registered.");
            }
            Class controller = BeanOperationUtils.getBeansFromInterface(requestModel).getClass();
            StringBuffer[] restUri = new StringBuffer[1];
            if (requestModel.isAnnotationPresent(BindURL.class)) {
                String[] uris = requestModel.getAnnotation(BindURL.class).value();
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
            EasyRestDistributedServiceBind.addService(requestModel, controller);
        }
        ActorFactory.createActor(NettyLaunch.class).tell(easyRest, ActorRef.noSender());
    }

}
