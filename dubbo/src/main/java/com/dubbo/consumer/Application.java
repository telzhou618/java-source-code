package com.dubbo.consumer;

import com.dubbo.api.UserService;
import com.dubbo.framework.ProxyFactory;

/**
 * @author telzhou
 * @since 2021/6/11
 */
public class Application {

    public static void main(String[] args) {

        UserService userService = ProxyFactory.getProxyService(UserService.class);
        System.out.println(userService.findUser(1));
    }
}
