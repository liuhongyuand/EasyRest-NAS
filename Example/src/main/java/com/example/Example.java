package com.example;

import com.easyrest.EasyRest;
import com.example.rest.HomeRestModel;

public class Example {

    public static void main(String[] args) {
        EasyRest easyRest = new EasyRest();
        easyRest.startServer();
        EasyRest.register(HomeRestModel.class);
    }

}
