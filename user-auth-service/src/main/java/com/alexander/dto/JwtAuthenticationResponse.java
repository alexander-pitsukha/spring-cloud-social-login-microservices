package com.alexander.dto;

import lombok.Value;

@Value
public class JwtAuthenticationResponse {

    String accessToken;

    boolean authenticated;

    UserInfo user;

}
