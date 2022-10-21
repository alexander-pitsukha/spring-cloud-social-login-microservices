package com.alexander.config;

import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GatewayConfig {

    private static final String USER_AUTH_SERVICE = "user-auth-service";
    private static final String PRODUCT_SERVICE = "product-service";
    private static final String ORDER_SERVICE = "order-service";
    public static final String REGEX = "/(?<path>.*)";
    public static final String REPLACEMENT = "/${path}";
    public static final String LB = "lb://";

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder, AuthenticationFilter filter, @Value("${server.port}") String serverPort) {
        return builder.routes()
                .route(USER_AUTH_SERVICE,
                        r -> r.path("/" + USER_AUTH_SERVICE + "/**", "/oauth2/authorization/**", "/login/oauth2/code/**").filters(f -> f.filter(filter)
                                .rewritePath(USER_AUTH_SERVICE + REGEX, REPLACEMENT)
                                .circuitBreaker(config -> config.setName("user-service-circuit-breaker")
                                        .setFallbackUri("forward:/user-auth-fallback"))).uri(LB + USER_AUTH_SERVICE))
                .route(PRODUCT_SERVICE,
                        r -> r.path("/" + PRODUCT_SERVICE + "/**").filters(f -> f.filter(filter)
                                .rewritePath(PRODUCT_SERVICE + REGEX, REPLACEMENT)).uri(LB + PRODUCT_SERVICE))
                .route(ORDER_SERVICE,
                        r -> r.path("/" + ORDER_SERVICE + "/**").filters(f -> f.filter(filter)
                                .rewritePath(ORDER_SERVICE + REGEX, REPLACEMENT)).uri(LB + ORDER_SERVICE))
                .route("openapi",
                        r -> r.path("/v3/api-docs/**")
                                .filters(f -> f.rewritePath("/v3/api-docs" + REGEX, REPLACEMENT + "/v3/api-docs"))
                                .uri("http://localhost:" + serverPort))
                .build();
    }

    @Bean
    public CommandLineRunner groupedOpenApis(RouteLocator locator, SwaggerUiConfigParameters swaggerUiParameters) {
        List<Route> routes = locator.getRoutes().collectList().block();
        assert routes != null;
        return args -> routes.stream()
                .map(Route::getId)
                .filter(id -> id.matches(".*-service"))
                .forEach(swaggerUiParameters::addGroup);
    }

}
