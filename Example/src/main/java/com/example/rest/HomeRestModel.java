package com.example.rest;

import com.example.controller.HomeController;
import com.easyrest.annotations.method.BindURL;
import com.easyrest.annotations.method.Post;
import com.easyrest.model.request.AbstractRequestModel;

import java.util.List;

@BindURL({"/rest1", "/rest2"})
public class HomeRestModel extends AbstractRequestModel {

    @Post("/getEmployeeList")
    public Class putList(List<Integer> list, String name){
        return HomeController.class;
    }

}
