package com.easyrest;

import com.easyrest.controller.EasyRestController;
import com.easyrest.netty.exception.ConfigurationException;
import io.netty.bootstrap.ServerBootstrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EasyRest {

    private Map<List<String>, EasyRestController> restControllerMap = new HashMap<>();

    public void startServer(){
        NettyInit defaultNetty = new NettyInit();
        defaultNetty.bindChannelFuture(defaultNetty.build().bind(defaultNetty.getPort()));
    }

    public void startServer(String SystemName, NettyInit nettyInit){
        if (nettyInit == null){
            throw new ConfigurationException(String.format("%s can not be null.", NettyInit.class.getName()));
        }
        ServerBootstrap bootstrap = nettyInit.build(SystemName);
        nettyInit.bindChannelFuture(bootstrap.bind(nettyInit.getPort()));
    }
}
