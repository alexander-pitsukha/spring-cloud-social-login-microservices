package com.alexander.converter;

import com.alexander.dto.OrderDto;
import com.alexander.model.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderToOrderDtoConverter implements Converter<Order, OrderDto> {

    @Override
    public OrderDto convert(Order source) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(source.getId());
        orderDto.setUserId(source.getUserId());
        orderDto.setRazorpayPaymentId(source.getRazorpayPaymentId());
        orderDto.setRazorpayOrderId(source.getRazorpayOrderId());
        orderDto.setRazorpaySignature(source.getRazorpaySignature());
        return orderDto;
    }

}
