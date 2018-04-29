package com.example.model;

public class Stock {

    private int code;

    private String name;

    public Stock(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
