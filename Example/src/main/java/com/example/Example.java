package com.example;

import com.easyrest.EasyRest;
import com.easyrest.network.NettyInit;
import com.example.rest.PeopleRestEndPoint;
import com.example.rest.StockInfoRest;

public class Example {

    public static final int port = 8080;

    public static void main(String[] args) {
        NettyInit nettyInit = new NettyInit();
        nettyInit.setPort(port);
        EasyRest easyRest = new EasyRest("classpath:applicationContext.xml");
        easyRest.registerServiceAndStartup("EasyRest_" + port, nettyInit, PeopleRestEndPoint.class, StockInfoRest.class);
    }

}
