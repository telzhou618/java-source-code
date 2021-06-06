package com.example.springframework.webmvc.mapping;

import com.example.springframework.webmvc.interceptor.HandlerInterceptor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhougaojun
 */
@Getter
@Setter
public class HandlerExecutionChain {

    private final Object handler;

    private HandlerInterceptor[] interceptors;

    public HandlerExecutionChain(Object handler) {
        this.handler = handler;
    }
}
