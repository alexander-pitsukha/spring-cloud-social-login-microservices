package com.alexander.dto.payment;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class OrderRequest {

    private Long userID;

    private String customerName;

    @Email
    private String email;

    private String phoneNumber;

    private String amount;

}
