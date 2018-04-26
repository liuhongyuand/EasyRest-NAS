package com.example.rest;

import com.easyrest.annotations.bean.BindController;
import com.easyrest.annotations.method.BindURL;
import com.easyrest.annotations.method.Get;
import com.easyrest.annotations.parameter.FromPath;
import com.easyrest.model.ResponseEntity;
import com.example.controller.StockInfoRestController;

@BindURL("/rest/{TENANT}/stock")
@BindController(StockInfoRestController.class)
public interface StockInfoRest {

    @Get("/favorite/{ID}/info")
    ResponseEntity getStockInfo(@FromPath String ID);

    @Get("/favorite/stocks")
    ResponseEntity getStockList();

}
