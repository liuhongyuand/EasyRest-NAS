package com.example.rest;

import com.easyrest.annotations.method.BindURL;
import com.easyrest.annotations.method.Get;
import com.easyrest.annotations.method.Post;
import com.easyrest.model.ResponseEntity;
import com.example.controller.HomeController;
import com.example.model.People;

import java.util.List;

@BindURL({"/rest"})
public interface HomeRestModel {

    @Get(url = "/getName", controller = HomeController.class)
    ResponseEntity getName();

    @Post(url = "/addPeople", controller = HomeController.class)
    ResponseEntity addPeople(People people);

    @Post(url = "/addPeoples", controller = HomeController.class)
    ResponseEntity addPeoples(List<People> peoples);

    @Post(url = "/addHomeList", controller = HomeController.class)
    ResponseEntity addHomeList(List<String> name, Integer age, long birth);

    @Get(url = "/getHome", controller = HomeController.class)
    ResponseEntity getHomeList(String name);
}
