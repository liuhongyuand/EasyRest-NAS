package com.example.cluster;

import com.easyrest.annotations.bean.BindController;
import com.easyrest.annotations.method.BindURL;
import com.easyrest.annotations.method.Get;
import com.easyrest.model.ResponseEntity;

@BindURL("/service1")
@BindController(Service1Impl.class)
public interface Service1 {

    @Get
    ResponseEntity getService1();

}
