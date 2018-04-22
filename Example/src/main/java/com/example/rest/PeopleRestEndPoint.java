package com.example.rest;

import com.easyrest.annotations.method.BindURL;
import com.easyrest.annotations.method.Get;
import com.easyrest.annotations.method.Post;
import com.easyrest.model.ResponseEntity;
import com.example.controller.PeopleController;
import com.example.model.People;

import java.util.List;

@BindURL({"/people"})
public interface PeopleRestEndPoint {

    @Get(url = "/getNameList", controller = PeopleController.class)
    ResponseEntity getAllName();

    @Get(url = "/getPeople", controller = PeopleController.class)
    ResponseEntity getPeople(String name, int age);

    @Post(url = "/addPeople", controller = PeopleController.class)
    ResponseEntity addPeople(People people);

    @Post(url = "/addPeoples", controller = PeopleController.class)
    ResponseEntity addBatch(List<People> peoples);

    @Post(url = "/addPeoplesWithDetails", controller = PeopleController.class)
    ResponseEntity addBatchWithDetails(List<People> peoples, List<String> name, List<Integer> age, long birth);
}
