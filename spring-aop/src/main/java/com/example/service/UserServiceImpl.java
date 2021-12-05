package com.example.service;

import org.springframework.stereotype.Service;

/**
 * @author gaojun.zhou
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public void sayHello() {
        System.out.println("hello world !");
    }
}
