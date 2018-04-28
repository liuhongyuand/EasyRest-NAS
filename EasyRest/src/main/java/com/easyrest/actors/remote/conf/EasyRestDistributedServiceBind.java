package com.easyrest.actors.remote.conf;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EasyRestDistributedServiceBind {

    private static boolean initFinished = false;

    private static ServiceMapping serviceMapping = null;

    private static final List<String> localService = new ArrayList<>();

    private static final Map<String, ServiceInfo> SERVICE_INFO_MAP = new ConcurrentHashMap<>();

    public static void loadConfiguration(InputStream resourceAsStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
        String temp;
        StringBuilder configuration = new StringBuilder();
        while ((temp = bufferedReader.readLine()) != null){
            configuration.append(temp);
        }
        bufferedReader.close();
        resourceAsStream.close();
        serviceMapping = new Gson().fromJson(configuration.toString(), ServiceMapping.class);
    }

    public static boolean isInitFinished() {
        return initFinished;
    }

    public static void setInitFinished(boolean initFinished) {
        EasyRestDistributedServiceBind.initFinished = initFinished;
    }

    public static void addService(Class aClass){
        localService.add(aClass.getName());
    }

    public static ServiceMapping getServiceMapping() {
        return serviceMapping;
    }

    public static List<String> getLocalService() {
        return localService;
    }

    public static Map<String, ServiceInfo> getServiceInfoMap() {
        return SERVICE_INFO_MAP;
    }
}
