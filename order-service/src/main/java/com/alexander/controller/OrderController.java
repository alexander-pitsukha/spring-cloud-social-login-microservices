package com.alexander.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.alexander.config.RazorPayClientConfig;
import com.alexander.dto.ApiResponse;
import com.alexander.dto.OrderDto;
import com.alexander.dto.payment.OrderRequest;
import com.alexander.dto.payment.OrderResponse;
import com.alexander.service.impl.OrderServiceIml;
import com.alexander.service.OrderService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("order")
@Slf4j
public class OrderController {

    private final RazorpayClient client;

    private final RazorPayClientConfig razorPayClientConfig;

    private final OrderService orderService;

    @Autowired
    public OrderController(RazorPayClientConfig razorpayClientConfig, OrderServiceIml orderService) throws RazorpayException {
        this.razorPayClientConfig = razorpayClientConfig;
        this.orderService = orderService;
        this.client = new RazorpayClient(razorpayClientConfig.getKey(), razorpayClientConfig.getSecret());
    }

    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse;
        try {
            String amountInPaise = convertRupeeToPaise(orderRequest.getAmount());
            Order order = createRazorPayOrder(amountInPaise);
            orderResponse = getOrderResponse(order.get("id"), amountInPaise);
            orderService.save(orderResponse.getRazorpayOrderId(), orderRequest.getUserID());
        } catch (RazorpayException e) {
            log.error("Exception while create payment order", e);
            return new ResponseEntity<>(new ApiResponse(false, "Error while create payment order: " + e.getMessage()), HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok(orderResponse);
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateOrder(@RequestBody OrderDto orderDto) {
        String errorMsg = orderService.validateAndUpdateOrder(orderDto.getRazorpayOrderId(), orderDto.getRazorpayPaymentId(), orderDto.getRazorpaySignature(),
                razorPayClientConfig.getSecret());
        if (errorMsg != null) {
            return new ResponseEntity<>(new ApiResponse(false, errorMsg), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new ApiResponse(true, orderDto.getRazorpayPaymentId()));
    }

    private OrderResponse getOrderResponse(String orderId, String amountInPaise) {
        OrderResponse razorPay = new OrderResponse();
        razorPay.setApplicationFee(amountInPaise);
        razorPay.setRazorpayOrderId(orderId);
        razorPay.setSecretKey(razorPayClientConfig.getKey());
        return razorPay;
    }

    private Order createRazorPayOrder(String amount) throws RazorpayException {
        JSONObject options = new JSONObject();
        options.put("amount", amount);
        options.put("currency", "INR");
        options.put("receipt", "txn_123456");
        return client.orders.create(options);
    }

    private String convertRupeeToPaise(String paise) {
        BigDecimal b = new BigDecimal(paise);
        BigDecimal value = b.multiply(BigDecimal.valueOf(100));
        return value.setScale(0, RoundingMode.UP).toString();
    }

}
