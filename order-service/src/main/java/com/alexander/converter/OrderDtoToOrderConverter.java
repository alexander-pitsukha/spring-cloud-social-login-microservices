package com.alexander.converter;

import com.alexander.dto.OrderDto;
import com.alexander.model.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoToOrderConverter implements Converter<OrderDto, Order> {

    @Override
    public Order convert(OrderDto source) {
        Order order = new Order();
        order.setUserId(source.getUserId());
        order.setRazorpayPaymentId(source.getRazorpayPaymentId());
        order.setRazorpayOrderId(source.getRazorpayOrderId());
        order.setRazorpaySignature(source.getRazorpaySignature());
        return order;
    }

}
