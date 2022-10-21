package com.alexander.service;

import com.alexander.dto.OrderDto;

public interface OrderService extends Service<OrderDto, Long> {

    OrderDto save(final String razorpayOrderId, final Long userId);

    String validateAndUpdateOrder(final String razorpayOrderId, final String razorpayPaymentId, final String razorpaySignature, final String secret);

}
