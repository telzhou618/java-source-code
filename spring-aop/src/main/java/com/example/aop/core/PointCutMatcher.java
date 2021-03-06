package com.example.aop.core;

import java.lang.reflect.Method;

/**
 * @author telzhou
 * @since 2021/12/14
 */
public interface PointCutMatcher {

    /**
     * 根据类匹配
     * @param clazz
     * @return
     */
    boolean matchClass(Class<?> clazz);

    /**
     * 根据方法匹配
     * @param method
     * @param targetClazz
     * @return
     */
    boolean matchMethod(Method method, Class<?> targetClazz);
}
