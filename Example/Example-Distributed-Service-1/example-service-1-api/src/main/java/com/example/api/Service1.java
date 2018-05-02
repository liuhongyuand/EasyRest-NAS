package com.example.api;

import tech.dbgsoftware.easyrest.annotations.method.BindURL;
import tech.dbgsoftware.easyrest.annotations.method.Post;
import tech.dbgsoftware.easyrest.annotations.parameter.AllDefined;
import tech.dbgsoftware.easyrest.model.ResponseEntity;
import com.example.model.People;

import java.util.List;

@BindURL("/service1")
public interface Service1 {

    @Post
    @AllDefined
    ResponseEntity createPeople(String name, int age, long birthday, List<String> skills, People boss);

}
