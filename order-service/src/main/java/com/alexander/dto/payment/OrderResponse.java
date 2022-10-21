package com.alexander.dto.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {

    private String applicationFee;

    private String razorpayOrderId;

    private String secretKey;

}
