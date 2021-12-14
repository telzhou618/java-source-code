package com.example.service.impl;

import com.example.SpringTests;
import com.example.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author telzhou
 * @since 2021/12/14
 */
public class UserServiceImplTest extends SpringTests {

    @Autowired
    private UserService userService;

    @Test
    public void sayHello() {
        userService.sayHello();
    }

    @Test
    public void testException() {
        userService.testException();
    }
}