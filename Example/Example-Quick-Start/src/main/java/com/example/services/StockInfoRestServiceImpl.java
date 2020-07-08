package com.example.services;

import tech.dbgsoftware.easyrest.model.ResponseEntity;
import com.example.model.Stock;
import com.example.rest.StockInfoRest;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockInfoRestServiceImpl implements StockInfoRest {

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

    @Override
    public ResponseEntity getA(String a) {
        return ResponseEntity.buildOkResponse(a);
    }
}
