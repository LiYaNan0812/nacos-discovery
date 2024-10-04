package com.lyn.controller;

import com.lyn.config.ProviderConfiguration;
import com.lyn.microservice.service1.api.Service1Api;
import com.lyn.microservice.service2.api.Service2Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@Slf4j
@RefreshScope
public class RestConsumerController {
//    @Value("${provider.ip}")
//    private String providerIp;
//    @Value("${provider.port}")
//    private String providerPort;

    @Autowired
    private ProviderConfiguration configuration;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("service")
    public String consumer() {
        String url = "http://" + configuration.getIp() + ":" + configuration.getPort() + "/service";
        String result = restTemplate.getForObject(url, String.class);
        log.info("consumer invoke");
        System.out.println(result);
        return "consumer invoke:" + result;
    }

    //使用服务发现中心，发现并访问服务
    String serviceName = "springcloud-restful-provider";

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("service2")
    public String consumer2() {

        ServiceInstance providerInstance = loadBalancerClient.choose(serviceName);
        URI uri = providerInstance.getUri();
        String result = restTemplate.getForObject(uri + "service", String.class);
        log.info("consumer invoke");
        System.out.println(result);
        return "consumer invoke:" + result;
    }

    //使用dubbo进行远程方法调用
    //1.使用dubbo的@Reference注解引入被调用的远程方法的对象
    @Reference
    Service2Api service2Api;

    @GetMapping("service3")
    public String service3() {
        String ser = service2Api.dubboService2();
        log.info("service2Api:" + ser);
        return "consumer dubbo invoke|" + ser;
    }

    @org.apache.dubbo.config.annotation.Reference
    Service1Api service1Api;
    @GetMapping("service4")
    public String service4() {
        String ser = service1Api.dubboService1();
        log.info("ser:" + ser);
        return "consumer dubbo invoke|" + ser;
    }

    //@Value注解配合@RefreshScop动态刷新配置
    @Value("${common.name}")
    private String commonName;

    //使用applicationContext动态获取配置
    @Autowired
    ConfigurableApplicationContext applicationContext;

    @Value("${common.address}")
    private String comonAddress;

    @GetMapping("configures")
    public String configs(){
        String commonName1 = applicationContext.getEnvironment().getProperty("common.name");
        return commonName +" | " + commonName1 + "|" +comonAddress;
    }
}
