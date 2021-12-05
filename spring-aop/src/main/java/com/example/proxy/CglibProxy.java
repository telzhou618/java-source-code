package com.example.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * cglib 动态代理,可以代理普通类(没有实现接口的类)
 *
 * @author jameszhou
 */
public class CglibProxy {

    private Enhancer enhancer = new Enhancer();

    public CglibProxy(Class targetClass, MethodInterceptor methodInterceptor) {
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(methodInterceptor);
    }

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
}
