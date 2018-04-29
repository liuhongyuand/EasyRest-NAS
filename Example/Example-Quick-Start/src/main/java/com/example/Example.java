package com.example;

import com.easyrest.EasyRest;

public class Example {

    public static void main(String[] args) {
        EasyRest easyRest = new EasyRest("classpath:MyExampleApplicationContext.xml");
        easyRest.startup("EasyRestServer");
    }

}
