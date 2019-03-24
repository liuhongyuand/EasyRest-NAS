package com.example.rest;

import tech.dbgsoftware.easyrest.annotations.method.BindURL;
import tech.dbgsoftware.easyrest.annotations.method.Get;

@BindURL("/test")
public interface TestRestService {

    @Get
    String ping();

}
