package com.example.cluster;

import com.easyrest.ioc.remote.EasyRestServiceLookup;
import com.easyrest.model.ResponseEntity;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;

@Controller
public class Service1Impl implements Service1 {

    @Override
    public ResponseEntity getService1(String name) {
        Service2 service2 = EasyRestServiceLookup.lookup(Service2.class);
        System.out.println(new Gson().toJson(service2.getPeople2(name)));
        return ResponseEntity.buildOkResponse("SERVICE-1");
    }

}
