package com.example.service;

import com.example.SpringTests;
import com.example.service.impl.OrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author telzhou
 * @since 2021/12/14
 */
public class OrderServiceTest extends SpringTests {

    @Autowired
    private OrderService orderService;

    @Test
    public void getOrderId() {
        System.out.println(orderService.getOrderId());
    }
}