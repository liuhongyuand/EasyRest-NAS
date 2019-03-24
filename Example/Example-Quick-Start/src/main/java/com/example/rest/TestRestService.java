package com.example.rest;

import tech.dbgsoftware.easyrest.annotations.method.BindURL;
import tech.dbgsoftware.easyrest.annotations.method.Get;
import tech.dbgsoftware.easyrest.annotations.method.Post;
import tech.dbgsoftware.easyrest.annotations.parameter.AllDefined;

@BindURL("/test")
public interface TestRestService {

    @Get
    String ping();

    @Post
    @AllDefined
    String test(String a, String b);

}
