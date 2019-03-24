package com.example.services;

import com.example.rest.TestRestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestRestService {

    @Override
    public String ping() {
        return ("Pong");
    }

}
