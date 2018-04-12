package com.easyrest.netty.core.api;

import java.util.Map;

public interface BaseConfiguration {

    int getPort();

    int getIoExecutors();

    String getSystemName();

    int getMaxContentLength();

    Map<String, Object> getCustomerProperties();

}
