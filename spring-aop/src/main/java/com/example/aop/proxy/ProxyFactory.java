package com.example.aop.proxy;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Proxy;

/**
 * @author telzhou
 * @since 2021/12/7
 */
@Getter
@Setter
public class ProxyFactory extends AdvisedSupport {

    public Object getProxy() {
        return createAopProxy(this).getProxy();
    }

    private AopProxy createAopProxy(AdvisedSupport advisedSupport) {
        // 被代理对象是接口 || 已经是代理对象 || 实现接口 走JDK动态代理，来自Spring AOP
        Class<?> targetClass = advisedSupport.getTarget().getClass();
        if (targetClass.isInterface()
                || Proxy.isProxyClass(targetClass)
                || targetClass.getInterfaces().length > 0) {
            return new JdkDynamicAopProxy(advisedSupport);
        } else {
            return new CglibAopProxy(advisedSupport);
        }
    }
}
