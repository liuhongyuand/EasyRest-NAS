package com.example.api;


import com.easyrest.annotations.method.BindURL;
import com.easyrest.annotations.method.Get;
import com.example.model.People;

@BindURL("/service2")
public interface Service2 {

    @Get
    People getPeople2(String name);

}
