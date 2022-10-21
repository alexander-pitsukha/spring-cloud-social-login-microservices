package com.alexander.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto extends AbstractLongIdentifiableDto {

    private Long userId;

    private String razorpayPaymentId;

    private String razorpayOrderId;

    private String razorpaySignature;

}
