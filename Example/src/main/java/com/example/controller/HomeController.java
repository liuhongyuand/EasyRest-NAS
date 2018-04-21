package com.example.controller;

import com.easyrest.model.ResponseEntity;
import com.example.rest.HomeRestModel;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class HomeController implements HomeRestModel{

    @Override
    public ResponseEntity addHomeList(List<String> list, String name) {
        return ResponseEntity.buildOkResponse("It Works! addHomeList");
    }

    @Override
    public ResponseEntity getHomeList(String name) {
        return ResponseEntity.buildOkResponse("It Works! getHomeList");
    }

}
