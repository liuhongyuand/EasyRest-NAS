package com.easyrest.ioc.utils;

import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

@Component
public class StaticClassUtils {

    private static ParameterNameDiscoverer parameterNameDiscoverer;

    public void setParameterNameDiscoverer(ParameterNameDiscoverer _parameterNameDiscoverer){
        parameterNameDiscoverer = _parameterNameDiscoverer;
    }

    public static ParameterNameDiscoverer getParameterNameDiscoverer() {
        return parameterNameDiscoverer;
    }
}
