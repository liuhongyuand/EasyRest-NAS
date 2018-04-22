package com.example.controller;

import com.easyrest.model.ResponseEntity;
import com.example.model.People;
import com.example.rest.HomeRestModel;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class HomeController implements HomeRestModel{

    @Override
    public ResponseEntity addHomeList(List<String> name, Integer age, long birth) {
        return ResponseEntity.buildOkResponse(Lists.newArrayList(name, age, birth));
    }

    @Override
    public ResponseEntity getHomeList(String name) {
        return ResponseEntity.buildOkResponse("It Works! getHomeList");
    }

    @Override
    public ResponseEntity getName() {
        return ResponseEntity.buildOkResponse();
    }

    @Override
    public ResponseEntity addPeople(People people){
        return ResponseEntity.buildOkResponse(people);
    }

    @Override
    public ResponseEntity addPeoples(List<People> peoples) {
        return ResponseEntity.buildOkResponse(peoples);
    }

}
