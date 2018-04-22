package com.example;

import com.easyrest.EasyRest;
import com.example.rest.PeopleRestEndPoint;

public class Example {

    public static void main(String[] args) {
        EasyRest easyRest = new EasyRest("classpath:applicationContext.xml");
        easyRest.registerServiceAndStartup(PeopleRestEndPoint.class);
    }

}
