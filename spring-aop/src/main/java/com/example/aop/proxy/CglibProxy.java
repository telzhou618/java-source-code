package com.example.aop.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib 动态代理,可以代理普通类(没有实现接口的类)
 *
 * @author jameszhou
 */
public class CglibProxy implements AopProxy, MethodInterceptor {

    private Enhancer enhancer = new Enhancer();

    private final AdvisedSupport advised;

    public CglibProxy(AdvisedSupport advisedSupport) {
        this.advised = advisedSupport;
        enhancer.setSuperclass(advisedSupport.getTarget().getClass());
        enhancer.setCallback(this);
    }
    @Override
    public Object getProxy() {
        return enhancer.create();
    }

    /**
     * 解析参数
     *
     * @param objects 参数列表
     * @return 参数解析结果
     */
    private String parseParams(Object[] objects) {
        if (objects == null || objects.length == 0) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        for (Object object : objects) {
            buffer.append(object.toString());
        }
        return buffer.toString();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return null;
    }
}
