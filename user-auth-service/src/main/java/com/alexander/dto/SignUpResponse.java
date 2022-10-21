package com.alexander.dto;

import lombok.Value;

@Value
public class SignUpResponse {

    boolean using2FA;

    String qrCodeImage;

}
