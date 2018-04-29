package com.example.controller;

import com.easyrest.model.ResponseEntity;
import com.example.model.Stock;
import com.example.rest.StockInfoRest;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class StockInfoRestController implements StockInfoRest {

    @Override
    public void addFavorite(String TENANT, String USER_ID, String CODE, long time) {
        System.out.println(TENANT + " " + USER_ID + " " + CODE + " " + time);
    }

    @Override
    public ResponseEntity addStocks(int userNumber, String userName, List<Stock> stockList) {
        return ResponseEntity.buildOkResponse(Lists.asList(userNumber, userName, new List[]{stockList}));
    }

    @Override
    public List<Stock> getStockList(String USER_ID) {
        return Lists.newArrayList(new Stock(100000, "stock1"), new Stock(100001, "stock2"), new Stock(100002, "stock3"));
    }
}
