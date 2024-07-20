package com.lyn.controller;

import com.lyn.config.ProviderConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class RestConsumerController {
//    @Value("${provider.ip}")
//    private String providerIp;
//    @Value("${provider.port}")
//    private String providerPort;

        @Autowired
        private ProviderConfiguration configuration;
        private RestTemplate restTemplate = new RestTemplate();

        @GetMapping("service")
        public String consumer() {
                String url = "http://" + configuration.getIp() + ":" + configuration.getPort() + "/service";
                String result = restTemplate.getForObject(url, String.class);
                log.info("consumer invoke");
                System.out.println(result);
                return "consumer invoke:" + result;
        }
}
