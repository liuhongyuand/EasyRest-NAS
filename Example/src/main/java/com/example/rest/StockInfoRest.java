package com.example.rest;

import com.easyrest.annotations.bean.BindController;
import com.easyrest.annotations.method.BindURL;
import com.easyrest.annotations.method.Get;
import com.easyrest.model.ResponseEntity;
import com.example.controller.StockInfoRestController;

@BindURL("/stock")
@BindController(StockInfoRestController.class)
public interface StockInfoRest {

    @Get
    ResponseEntity getStockInfo();

}
