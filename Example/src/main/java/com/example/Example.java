package com.example;

import com.easyrest.EasyRest;
import com.example.rest.StockInfoRest;

public class Example {

    public static void main(String[] args) {
        EasyRest easyRest = new EasyRest("classpath:MyExampleApplicationContext.xml");
        easyRest.registerServiceAndStartup("EasyRestServer", StockInfoRest.class);
    }

}
