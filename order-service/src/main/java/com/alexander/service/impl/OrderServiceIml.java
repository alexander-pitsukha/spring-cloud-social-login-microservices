package com.alexander.service.impl;

import com.alexander.dto.OrderDto;
import com.alexander.model.Order;
import com.alexander.repo.OrderRepository;
import com.alexander.util.Signature;
import com.alexander.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OrderServiceIml implements OrderService {

    private final OrderRepository orderRepository;
    private final DefaultConversionService defaultConversionService;

    @Transactional
    @Override
    public String validateAndUpdateOrder(final String razorpayOrderId, final String razorpayPaymentId, final String razorpaySignature, final String secret) {
        String errorMsg = null;
        try {
            Order order = orderRepository.findByRazorpayOrderId(razorpayOrderId);
            String generatedSignature = Signature.calculateRFC2104HMAC(order.getRazorpayOrderId() + "|" + razorpayPaymentId, secret);
            if (generatedSignature.equals(razorpaySignature)) {
                order.setRazorpayOrderId(razorpayOrderId);
                order.setRazorpayPaymentId(razorpayPaymentId);
                order.setRazorpaySignature(razorpaySignature);
                orderRepository.save(order);
            } else {
                errorMsg = "Payment validation failed: Signature doesn't match";
            }
        } catch (Exception e) {
            log.error("Payment validation failed", e);
            errorMsg = e.getMessage();
        }
        return errorMsg;
    }

    @Override
    public Page<OrderDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(order -> defaultConversionService.convert(order, OrderDto.class));
    }

    @Override
    public OrderDto findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        return defaultConversionService.convert(order, OrderDto.class);
    }

    @Transactional
    @Override
    public OrderDto save(OrderDto orderDto) {
        Order order = Objects.requireNonNull(defaultConversionService.convert(orderDto, Order.class));
        order = orderRepository.save(order);
        return defaultConversionService.convert(order, OrderDto.class);
    }

    @Transactional
    @Override
    public OrderDto save(final String razorpayOrderId, final Long userId) {
        Order order = new Order();
        order.setRazorpayOrderId(razorpayOrderId);
        order.setUserId(userId);
        order = orderRepository.save(order);
        return defaultConversionService.convert(order, OrderDto.class);
    }

    @Transactional
    @Override
    public void update(Long id, OrderDto orderDto) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setUserId(orderDto.getUserId());
        order.setRazorpayPaymentId(orderDto.getRazorpayPaymentId());
        order.setRazorpayOrderId(orderDto.getRazorpayOrderId());
        order.setRazorpaySignature(orderDto.getRazorpaySignature());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

}
