package com.example.cluster;

import com.easyrest.annotations.bean.BindController;
import com.easyrest.annotations.method.BindURL;
import com.easyrest.annotations.method.Get;

@BindURL("/service2")
@BindController(Service2Impl.class)
public interface Service2 {

    @Get
    People getPeople2();

}
