package com.example.service.impl;

import com.example.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 实现接口的类，走jdk动态代理
 *
 * @author gaojun.zhou
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public void sayHello() {
        System.out.println("hello world !");
    }

    @Override
    public void testException() {
        System.out.println(1 / 0);
    }
}
