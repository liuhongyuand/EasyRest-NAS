package com.easyrest.network.router;

import com.easyrest.model.request.RestObject;

import java.util.HashMap;
import java.util.Map;

public class RouterProvider {

    private static Map<String, RestObject> restObjectMap = new HashMap<>();

    public static Map<String, RestObject> getRestObjectMap() {
        return restObjectMap;
    }
}
