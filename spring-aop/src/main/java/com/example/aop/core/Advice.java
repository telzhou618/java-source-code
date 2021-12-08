package com.example.aop.core;

/**
 * @author zhougaojun
 * @since 2021/12/7
 */
public interface Advice {

    void invoke(Object[] args);
}
