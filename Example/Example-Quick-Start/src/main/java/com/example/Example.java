package com.example;


import tech.dbgsoftware.easyrest.EasyRest;
import tech.dbgsoftware.easyrest.network.NettyInit;

public class Example {

    public static void main(String[] args) {
        EasyRest easyRest = new EasyRest("classpath:MyExampleApplicationContext.xml");
        easyRest.startup("EasyRestServer", new NettyInit("127.0.0.1", 8181));
    }

}
