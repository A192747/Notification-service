package org.oril.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@Configuration
@EnableHystrix
public class GatewayConfig {

    @Autowired
    private AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://auth-service"))
                .route("contacts-service", r -> r.path("/contacts")
                        .filters(f -> f.filter(filter))
                        .uri("lb://contacts-service"))
                .route("message-handler-service", r -> r.path("/messages")
                        .filters(f -> f.filter(filter))
                        .uri("lb://message-handler-service"))
                // Add more routes here based on the configuration in application.yml
                .build();
    }
}