package com.example.main.impl;

import tech.dbgsoftware.easyrest.ioc.remote.EasyRestServiceLookup;
import tech.dbgsoftware.easyrest.model.ResponseEntity;
import com.example.api.Service1;
import com.example.api.Service2;
import com.example.model.People;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Service1Impl implements Service1 {

    @Override
    public ResponseEntity createPeople(String name, int age, long birthday, List<String> skills, People boss) {
        Service2 service2 = EasyRestServiceLookup.lookup(Service2.class);
        return ResponseEntity.buildOkResponse(service2.getPeople(name, age, birthday, skills, boss));
    }

}
