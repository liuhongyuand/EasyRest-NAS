package com.example.main.impl;

import com.example.api.Service2;
import com.example.model.People;
import org.springframework.stereotype.Controller;

@Controller
public class Service2Impl implements Service2 {

    @Override
    public People getPeople2(String name) {
        return new People(name, 18);
    }

}
