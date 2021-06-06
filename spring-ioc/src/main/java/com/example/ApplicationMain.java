package com.example;

import com.example.service.OrderService;
import com.example.service.RoleService;
import com.example.service.UserService;
import com.example.springframework.AnnotationConfigApplicationContext;

/**
 * @author zhougaojun
 */
public class ApplicationMain {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        // 原型 bean，每次getBean都会创建一个先bean返回
        UserService userService = context.getBean("userService");
        UserService userService1 = context.getBean("userService");
        System.out.println(userService);
        System.out.println(userService1);
        userService.print();
        System.out.println("================");
        // 单例bean 非懒加载 bean
        RoleService roleService = context.getBean("roleService");
        RoleService roleService1 = context.getBean("roleService");
        System.out.println(roleService);
        System.out.println(roleService1);
        System.out.println("================");
        // 单例bean 懒加载 bean
        OrderService orderService = context.getBean("orderService");
        OrderService orderService1 = context.getBean("orderService");
        System.out.println(orderService);
        System.out.println(orderService1);

    }
}
