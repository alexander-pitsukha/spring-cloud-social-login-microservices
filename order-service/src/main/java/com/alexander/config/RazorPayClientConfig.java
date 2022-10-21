package com.alexander.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Component
@ConfigurationProperties(prefix = "razorpay")
@Getter
@Setter
@Validated
public class RazorPayClientConfig {

    @NotBlank
    private String key;

    @NotBlank
    private String secret;

}
