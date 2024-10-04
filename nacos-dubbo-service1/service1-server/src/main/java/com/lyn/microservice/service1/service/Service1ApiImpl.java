package com.lyn.microservice.service1.service;

import com.lyn.microservice.service1.api.Service1Api;
import com.lyn.microservice.service2.api.Service2Api;

@org.apache.dubbo.config.annotation.Service
public class Service1ApiImpl implements Service1Api {

    @org.apache.dubbo.config.annotation.Reference
    Service2Api service2Api;

    @Override
    public String dubboService1() {
        String result = service2Api.dubboService2();
        return "service1 dubbo invoke |" + result;
    }
}
