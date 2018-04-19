package com.easyrest.controller;

import com.easyrest.model.HttpEntity;
import com.easyrest.model.ResponseEntity;

public interface EasyRestController<T> {

    ResponseEntity<T> doProcess(HttpEntity httpEntity);

}
