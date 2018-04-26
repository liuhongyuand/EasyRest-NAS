package com.example.controller;

import com.easyrest.model.ResponseEntity;
import com.example.Example;
import com.example.rest.StockInfoRest;
import org.springframework.stereotype.Controller;

@Controller
public class StockInfoRestController implements StockInfoRest {

    @Override
    public ResponseEntity getStockInfo(String ID) {
        return ResponseEntity.buildOkResponse(Example.port);
    }

    @Override
    public ResponseEntity getStockList() {
        return ResponseEntity.buildOkResponse();
    }
}
