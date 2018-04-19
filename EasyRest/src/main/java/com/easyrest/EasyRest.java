package com.easyrest;

import com.easyrest.annotations.method.Post;
import com.easyrest.controller.EasyRestController;
import com.easyrest.ioc.utils.BeanOperationUtils;
import com.easyrest.model.request.RequestModel;
import com.easyrest.netty.exception.ConfigurationException;
import com.easyrest.utils.LogUtils;
import com.sun.org.apache.xpath.internal.Arg;
import io.netty.bootstrap.ServerBootstrap;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EasyRest {


    public EasyRest(){
        BeanOperationUtils.setApplicationContext(new ClassPathXmlApplicationContext("applicationContext.xml"));
    }

    private Map<List<String>, EasyRestController> restControllerMap = new HashMap<>();

    public static void register(Class requestModel){
        try {
            if (requestModel.newInstance() instanceof RequestModel){
                for (Method method : requestModel.getMethods()){
                        if (method.isAnnotationPresent(Post.class)){
                            System.out.println(method.getName());
                            System.out.println(method.getParameters()[0].getParameterizedType());
                            Arg[] args = new Arg[method.getParameters().length];
                            for (int i = 0; i < args.length; i++) {
                                args[i] = null;
                            }
                            EasyRestController easyRestController = (EasyRestController) BeanOperationUtils.getBean((Class) method.invoke(requestModel.newInstance(), args));
                            easyRestController.doProcess(null);
                        }
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void startServer(){
        startServer(NettyInit.SystemName, new NettyInit());
    }

    public void startServer(String SystemName, NettyInit nettyInit){
        if (nettyInit == null){
            throw new ConfigurationException(String.format("%s can not be null.", NettyInit.class.getName()));
        }
        LogUtils.info(String.format("%s is running.", SystemName));
        ServerBootstrap bootstrap = nettyInit.build(SystemName);
        nettyInit.bindChannelFuture(bootstrap.bind(nettyInit.getPort()));
    }
}
