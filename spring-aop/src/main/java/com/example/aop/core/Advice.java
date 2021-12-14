package com.example.aop.core;

import java.lang.reflect.InvocationTargetException;

/**
 * @author telzhou
 * @since 2021/12/7
 */
public interface Advice {

    void invoke(Object[] args) throws InvocationTargetException, IllegalAccessException;
}
