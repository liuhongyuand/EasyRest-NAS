package com.example.services;

import com.example.rest.TestRestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestRestService {

    @Override
    public String ping() {
        return ("Pong");
    }

    @Override
    public String test(String a, String b) {
        String c;
        String d;
        String e;
        String f;
        String g;
        if (a.equalsIgnoreCase(b)){
            c = a;
            d = a;
            e = a;
            f = a;
            g = a;
        } else {
            c = a;
            d = b;
            e = "e";
            f = "f";
            g = "g";
        }
        return c + "-" + d + "-" + e + "-" + f + "-" + g;
    }

}
