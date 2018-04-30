package com.example.main.impl;

import com.example.api.Service2;
import com.example.model.People;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Service2Impl implements Service2 {

    @Override
    public People getPeople(String name, int age, long birthday, List<String> skills, People boss) {
        return new People(name, age, birthday, skills, boss);
    }
}
