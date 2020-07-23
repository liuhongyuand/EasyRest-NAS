package com.example;


import tech.dbgsoftware.easyrest.EasyRest;
import tech.dbgsoftware.easyrest.network.NettyInit;

public class Example {

    public static void main(String[] args) {
        NettyInit nettyInit = new NettyInit("127.0.0.1", 8181);
        nettyInit.setIoExecutors(1);
        EasyRest easyRest = new EasyRest("classpath:MyExampleApplicationContext.xml");
        easyRest.startup("EasyRestServer", nettyInit);
    }

}
