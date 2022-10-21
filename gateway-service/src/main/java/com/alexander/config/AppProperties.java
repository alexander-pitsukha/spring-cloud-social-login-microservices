package com.alexander.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final Auth auth = new Auth();

    @Getter
    @Setter
    @Validated
    public static class Auth {
        @NotBlank
        private String tokenSecret;
        @NotBlank
        private long tokenExpirationMsec;
    }

}
