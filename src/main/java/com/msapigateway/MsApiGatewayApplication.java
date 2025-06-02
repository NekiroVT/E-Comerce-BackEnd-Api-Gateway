package com.msapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // 👈 Para que se registre en Eureka (cuando lo tengas)
public class MsApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsApiGatewayApplication.class, args);
    }

}