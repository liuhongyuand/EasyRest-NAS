package com.example.services;

import com.example.rest.TestRestService;
import org.springframework.stereotype.Service;
import tech.dbgsoftware.easyrest.model.ResponseEntity;
import tech.dbgsoftware.easyrest.utils.LogUtils;

@Service
public class TestServiceImpl implements TestRestService {

    @Override
    public void ping(String a) {
        System.out.println(a);
    }

    @Override
    public void postPing(String a) {
        System.out.println(a);
    }

    @Override
    public ResponseEntity testApi(String a, String b) {
        LogUtils.info(a + " " + b);
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
