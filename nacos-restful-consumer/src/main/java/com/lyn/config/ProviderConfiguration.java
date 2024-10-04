package com.lyn.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "provider")
public class ProviderConfiguration {
    private String ip = "localhot";
    private String port = "8080";

    public static void main(String[] args) {
        System.out.println("String.valueOf(342) = " + String.valueOf(342));
        System.out.println();
    }
}
