package com.example.cluster;

import org.springframework.stereotype.Controller;

@Controller
public class Service2Impl implements Service2 {

    @Override
    public People getPeople2(String name) {
        return new People(name, 18);
    }

}
