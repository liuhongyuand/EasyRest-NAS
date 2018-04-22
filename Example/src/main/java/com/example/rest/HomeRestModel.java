package com.example.rest;

import com.easyrest.annotations.method.BindURL;
import com.easyrest.annotations.method.Get;
import com.easyrest.annotations.method.Post;
import com.easyrest.annotations.parameter.AllDefined;
import com.easyrest.annotations.parameter.Optional;
import com.easyrest.model.ResponseEntity;
import com.example.controller.HomeController;

@BindURL({"/rest"})
public interface HomeRestModel {

    @AllDefined
    @Post(url = "/addHomeList", controller = HomeController.class)
    ResponseEntity addHomeList(@Optional String name, Integer age, long birth);

    @Get(url = "/getHome", controller = HomeController.class)
    ResponseEntity getHomeList(@Optional String name);

}
