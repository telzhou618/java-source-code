package com.example.service.impl;

import org.springframework.stereotype.Service;

/**
 *  普通类, 用于Cglib代理
 * @author gaojun.zhou
 */
@Service
public class OrderService {

    public Integer getOrderId() {
        System.out.println("模拟获取订单信息业务");
        return 1;
    }
}
