package com.example.springframework.webmvc.mapping;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Method;

/**
 * @author zhougaojun
 */
@Getter
@Setter
@ToString
public class HandlerMethod {

    private final Object bean;

    private final Class<?> beanType;

    private final Method method;

    public HandlerMethod(Object bean, Class<?> beanType, Method method) {
        this.bean = bean;
        this.beanType = beanType;
        this.method = method;
    }

    public HandlerMethod(HandlerMethod handlerMethod) {
        this.bean = handlerMethod.bean;
        this.beanType = handlerMethod.beanType;
        this.method = handlerMethod.method;
    }
}
