//package com.msapigateway.config;
//
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Configuration
//public class GatewayHeaderConfig {
//
//    @Bean
//    public GlobalFilter addInternalHeaderFilter() {
//        return (exchange, chain) -> {
//            ServerHttpRequest originalRequest = exchange.getRequest();
//
//            ServerHttpRequest requestWithHeader = originalRequest.mutate()
//                    .headers(headers -> {
//                        if (!headers.containsKey("X-Internal-Call")) {
//                            headers.add("X-Internal-Call", "gateway");
//                        }
//                    })
//                    .build();
//
//            ServerWebExchange mutatedExchange = exchange.mutate()
//                    .request(requestWithHeader)
//                    .build();
//
//            return chain.filter(mutatedExchange);
//        };
//    }
//
//}
