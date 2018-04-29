package com.example.main;

import com.easyrest.EasyRest;
import com.easyrest.actors.remote.conf.EasyRestDistributedServiceBind;
import com.easyrest.network.NettyInit;

import java.io.IOException;

public class Startup {

    public static void main(String[] args) throws IOException {
        EasyRestDistributedServiceBind.loadConfiguration(Startup.class.getClassLoader().getResourceAsStream("services-mapping-02.json"));
        EasyRest easyRest = new EasyRest("classpath:MyExampleApplicationContext-02.xml");
        easyRest.startup("example-service-2", new NettyInit(8002));
    }

}
