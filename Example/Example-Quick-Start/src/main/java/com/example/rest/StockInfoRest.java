package com.example.rest;

import tech.dbgsoftware.easyrest.annotations.method.BindURL;
import tech.dbgsoftware.easyrest.annotations.method.Get;
import tech.dbgsoftware.easyrest.annotations.method.Post;
import tech.dbgsoftware.easyrest.annotations.parameter.AllDefined;
import tech.dbgsoftware.easyrest.model.ResponseEntity;
import com.example.model.Stock;

import java.util.List;

@BindURL("/rest/{TENANT}/stock")
public interface StockInfoRest {

    @Post("/personal/{USER_ID}/favorite/{CODE}")
    void addFavorite(String TENANT, String USER_ID, String CODE, long time);

    @Post
    @AllDefined
    ResponseEntity addStocks(int userNumber, String userName, List<Stock> stockList);

    @Get("/personal/{USER_ID}/favorite/list")
    List<Stock> getStockList(String USER_ID);

}
