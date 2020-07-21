package com.example.services;

import com.example.rest.TestRestService;
import org.springframework.stereotype.Service;
import tech.dbgsoftware.easyrest.model.ResponseEntity;

@Service
public class TestServiceImpl implements TestRestService {

    @Override
    public void ping() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("11");
    }

    @Override
    public ResponseEntity testApi(String a, String b) {
        return ResponseEntity.buildOkResponse(a + "-" + b);
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
