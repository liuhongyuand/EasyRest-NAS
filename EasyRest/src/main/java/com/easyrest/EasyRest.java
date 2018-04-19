package com.easyrest;

import com.easyrest.controller.EasyRestController;
import com.easyrest.model.request.RequestModel;
import com.easyrest.netty.exception.ConfigurationException;
import com.easyrest.utils.LogUtils;
import io.netty.bootstrap.ServerBootstrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EasyRest {

    private Map<List<String>, EasyRestController> restControllerMap = new HashMap<>();


    public static void register(RequestModel requestModel){

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
