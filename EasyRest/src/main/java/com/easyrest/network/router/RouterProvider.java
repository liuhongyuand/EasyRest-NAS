package com.easyrest.network.router;

import com.easyrest.model.request.RestObject;
import com.easyrest.utils.StringUtils;
import io.netty.handler.codec.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RouterProvider {

    private static Map<String, RestObject> restObjectMap = new HashMap<>();

    public static Map<String, RestObject> getRestObjectMap() {
        return restObjectMap;
    }

    public static RestObject getRestObject(String url) {
        String modifiedUrl = PathStore.queryPath(url);
        if (modifiedUrl != null) {
            if (modifiedUrl.contains("*")) {
                RestObject restObject = restObjectMap.get(modifiedUrl);
                if (restObject != null) {
                    String[] originPaths = StringUtils.split(restObject.getOriginalPath(), "/");
                    String[] modifiedPaths = StringUtils.split(modifiedUrl, "/");
                    String[] requestPaths = StringUtils.split(url, "/");
                    if (originPaths.length == requestPaths.length) {
                        for (int i = 0; i < originPaths.length; i++) {
                            if (modifiedPaths[i].equalsIgnoreCase("*")) {
                                restObject.putToUriValueMap(originPaths[i].replace("{", "").replace("}", ""), requestPaths[i]);
                            }
                        }
                    }
                }
            }
        }
        return restObjectMap.get(modifiedUrl);
    }

    public static void methodRouterResolve(StringBuffer[] restUri, String methodName, String urlFromMethod, HttpMethod httpMethod, Method method, Class controller){
        if (urlFromMethod.startsWith("/")){
            methodName = urlFromMethod;
        }
        RestObject restObject = new RestObject(method, httpMethod, controller);
        for (StringBuffer aRestUri : restUri) {
            putRouter(aRestUri.toString(), methodName, httpMethod, restObject);
        }
    }

    public static void registerUrl(String url, HttpMethod httpMethod, RestObject restObject){
        restObject.setOriginalPath(url);
        PathStore.putPath(url);
        String[] paths = StringUtils.split(url, "/");
        StringBuilder modifiedUrl = new StringBuilder();
        for (String path : paths) {
            modifiedUrl.append("/");
            if (path.startsWith("{") && path.endsWith("}")) {
                modifiedUrl.append("*");
            } else {
                modifiedUrl.append(path);
            }
        }
        if (restObjectMap.containsKey(modifiedUrl.toString())){
            restObjectMap.get(modifiedUrl.toString()).addHttpMethod(httpMethod);
        } else {
            restObjectMap.put(modifiedUrl.toString(), restObject);
        }
    }

    private static void putRouter(String bindUrl, String methodUrl, HttpMethod httpMethod, RestObject restObject){
        StringBuilder url = new StringBuilder(bindUrl);
        if (!bindUrl.startsWith("/")){
            url.insert(0, "/");
        }
        if (!bindUrl.endsWith("/") && !methodUrl.startsWith("/")){
            url.insert(bindUrl.length(), "/");
        }
        url.append(methodUrl);
        RouterProvider.registerUrl(url.toString(), httpMethod, restObject);
    }

}
