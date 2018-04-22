package com.example.controller;

import com.easyrest.model.ResponseEntity;
import com.example.rest.HomeRestModel;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController implements HomeRestModel{

    @Override
    public ResponseEntity addHomeList(String name, Integer age, long birth) {
        return ResponseEntity.buildOkResponse(Lists.newArrayList(name, age, birth));
    }

    @Override
    public ResponseEntity getHomeList(String name) {
        return ResponseEntity.buildOkResponse("It Works! getHomeList");
    }

}
