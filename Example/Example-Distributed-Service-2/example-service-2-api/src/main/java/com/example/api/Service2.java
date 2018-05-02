package com.example.api;


import tech.dbgsoftware.easyrest.annotations.method.BindURL;
import tech.dbgsoftware.easyrest.annotations.method.Get;
import com.example.model.People;

import java.util.List;

@BindURL("/service2")
public interface Service2 {

    @Get
    People getPeople(String name, int age, long birthday, List<String> skills, People boss);

}
