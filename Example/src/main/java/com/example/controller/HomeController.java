package com.example.controller;

import com.easyrest.controller.EasyRestController;
import com.easyrest.model.HttpEntity;
import com.easyrest.model.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController implements EasyRestController {

    @Override
    public ResponseEntity doProcess(HttpEntity httpEntity) {
        System.out.println("!!!!");
        return ResponseEntity.buildOkResponse("It Works!");
    }
}
