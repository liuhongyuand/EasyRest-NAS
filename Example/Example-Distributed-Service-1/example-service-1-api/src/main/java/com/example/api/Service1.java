package com.example.api;

import com.easyrest.annotations.method.BindURL;
import com.easyrest.annotations.method.Post;
import com.easyrest.annotations.parameter.AllDefined;
import com.easyrest.model.ResponseEntity;
import com.example.model.People;

import java.util.List;

@BindURL("/service1")
public interface Service1 {

    @Post
    @AllDefined
    ResponseEntity createPeople(String name, int age, long birthday, List<String> skills, People boss);

}
