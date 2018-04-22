package com.example.controller;

import com.easyrest.model.ResponseEntity;
import com.example.model.People;
import com.example.rest.PeopleRestEndPoint;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PeopleController implements PeopleRestEndPoint {

    @Override
    public ResponseEntity getPeople(String name, int age) {
        return ResponseEntity.buildOkResponse(new People(name, age));
    }

    @Override
    public ResponseEntity getAllName() {
        return ResponseEntity.buildCustomizeResponse(1, "ok", Lists.newArrayList("First", "Second"));
    }

    @Override
    public ResponseEntity addPeople(People people) {
        return ResponseEntity.buildOkResponse(people);
    }

    @Override
    public ResponseEntity addBatch(List<People> peoples) {
        return ResponseEntity.buildOkResponse(peoples);
    }

    @Override
    public ResponseEntity addBatchWithDetails(List<People> peoples, List<String> name, List<Integer> age, long birth) {
        return ResponseEntity.buildOkResponse(Lists.newArrayList(peoples, name, age, birth));
    }
}
