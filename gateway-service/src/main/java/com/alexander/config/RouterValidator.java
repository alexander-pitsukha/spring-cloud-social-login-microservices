package com.alexander.config;

import lombok.Getter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
@Getter
public class RouterValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/auth/signup",
            "/auth/signin",
            "/oauth2/authorization",
            "/login/oauth2/code/"
    );

    private final Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
