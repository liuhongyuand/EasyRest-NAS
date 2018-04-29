package com.example.cluster;

import com.easyrest.annotations.method.BindURL;
import com.easyrest.annotations.method.Get;
import com.easyrest.model.ResponseEntity;

@BindURL("/service1")
public interface Service1 {

    @Get
    ResponseEntity getService1(String name);

}
