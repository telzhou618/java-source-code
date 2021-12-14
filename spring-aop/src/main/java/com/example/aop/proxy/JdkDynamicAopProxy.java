package com.example.aop.proxy;

import com.example.aop.core.Advisor;
import com.example.aop.util.AopUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK 动态代理,代理类必须实现接口
 *
 * @author jameszhou
 */
public class JdkDynamicAopProxy  implements AopProxy, InvocationHandler {

    private final AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport advisedSupport) {
        this.advised = advisedSupport;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(advised.getClass().getClassLoader(), advised.getTarget().getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {

        Object target = advised.getTarget();
        Class<?> targetClass = target.getClass();

        // 排除 toString,equals,hashCode 方法无需增强
        if (ReflectionUtils.isEqualsMethod(method)
                || ReflectionUtils.isHashCodeMethod(method)
                || ReflectionUtils.isToStringMethod(method)) {
            return method.invoke(target, arguments);
        }
        // 切面类不走代理
        if (AopUtils.isAspectAnnotation(target)) {
            return method.invoke(target, arguments);
        }
        Object invoke = null;
        try {
            // 执行 before
            for (Advisor advisor : advised.getBeforeAdvisors(method, targetClass)) {
                advisor.getAdvice().invoke(arguments);
            }
            invoke = method.invoke(advised.getTarget(), arguments);
            // 执行 after
            for (Advisor advisor : advised.getAfterAdvisors(method, targetClass)) {
                advisor.getAdvice().invoke(arguments);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 执行 AfterThrowing
            for (Advisor advisor : advised.getAfterThrowingAdvisors(method, targetClass)) {
                advisor.getAdvice().invoke(arguments);
            }
        }
        return invoke;
    }
}
