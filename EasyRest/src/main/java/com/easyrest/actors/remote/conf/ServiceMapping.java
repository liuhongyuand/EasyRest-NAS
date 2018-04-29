package com.easyrest.actors.remote.conf;

import com.easyrest.actors.remote.model.ServiceInfo;

import java.util.List;

public class ServiceMapping {

    private ServiceInfo self;
    private List<ServiceInfo> services;

    public ServiceInfo getSelf() {
        return self;
    }

    public List<ServiceInfo> getServices() {
        return services;
    }
}
