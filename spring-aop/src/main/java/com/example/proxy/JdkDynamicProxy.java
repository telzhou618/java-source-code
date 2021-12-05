package com.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * JDK 动态代理,代理类必须实现接口
 *
 * @author jameszhou
 */
public class JdkDynamicProxy {

    private Class targetClazz;
    private InvocationHandler invocationHandler;

    public JdkDynamicProxy(Class targetClazz, InvocationHandler invocationHandler) {
        this.targetClazz = targetClazz;
        this.invocationHandler = invocationHandler;
    }

    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(targetClazz.getClassLoader(), targetClazz.getInterfaces(), invocationHandler);
    }
}
