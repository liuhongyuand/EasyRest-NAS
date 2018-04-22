package com.example.rest;

import com.easyrest.annotations.bean.BindController;
import com.easyrest.annotations.method.BindURL;
import com.easyrest.annotations.method.Get;
import com.easyrest.annotations.method.Post;
import com.easyrest.model.ResponseEntity;
import com.example.controller.PeopleController;
import com.example.model.People;

import java.util.List;

@BindURL({"/people"})
@BindController(PeopleController.class)
public interface PeopleRestEndPoint {

    @Get
    ResponseEntity getAllName();

    @Get
    ResponseEntity getPeople(String name, int age);

    @Post
    ResponseEntity addPeople(People people);

    @Post
    ResponseEntity addBatch(List<People> peoples);

    @Post
    ResponseEntity addBatchWithDetails(List<People> peoples, List<String> name, List<Integer> age, long birth);
}
