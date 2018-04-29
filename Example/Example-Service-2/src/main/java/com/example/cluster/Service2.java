package com.example.cluster;

import com.easyrest.annotations.method.BindURL;
import com.easyrest.annotations.method.Get;

@BindURL("/service2")
public interface Service2 {

    @Get
    People getPeople2(String name);

}
