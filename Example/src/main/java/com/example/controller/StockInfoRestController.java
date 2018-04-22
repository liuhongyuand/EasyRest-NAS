package com.example.controller;

import com.easyrest.model.ResponseEntity;
import com.example.Example;
import com.example.rest.StockInfoRest;
import org.springframework.stereotype.Controller;

@Controller
public class StockInfoRestController implements StockInfoRest {
    @Override
    public ResponseEntity getStockInfo() {
        return ResponseEntity.buildOkResponse(Example.port);
    }
}
