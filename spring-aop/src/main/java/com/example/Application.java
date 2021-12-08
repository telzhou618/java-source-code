package com.example;

import com.example.aop.annotation.EnableMyAspectJAutoProxy;
import com.example.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhougaojun
 */

@ComponentScan(basePackages = "com.example")
@EnableMyAspectJAutoProxy
public class Application {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        UserService userService = context.getBean(UserService.class);
        System.out.println(userService);
        userService.sayHello();
    }
}
