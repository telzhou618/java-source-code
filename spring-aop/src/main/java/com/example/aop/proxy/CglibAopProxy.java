package com.example.aop.proxy;

import com.example.aop.core.Advisor;
import com.example.aop.util.AopUtils;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * cglib 动态代理,可以代理普通类(没有实现接口的类)
 *
 * @author telzhou
 */
public class CglibAopProxy implements AopProxy, MethodInterceptor {

    private Enhancer enhancer = new Enhancer();

    private final AdvisedSupport advised;

    public CglibAopProxy(AdvisedSupport advisedSupport) {
        this.advised = advisedSupport;
        enhancer.setSuperclass(advisedSupport.getTarget().getClass());
        enhancer.setCallback(this);
    }

    @Override
    public Object getProxy() {
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        Object target = advised.getTarget();
        Class<?> targetClass = target.getClass();

        // 排除 toString,equals,hashCode 方法无需增强
        if (ReflectionUtils.isEqualsMethod(method)
                || ReflectionUtils.isHashCodeMethod(method)
                || ReflectionUtils.isToStringMethod(method)) {
            return method.invoke(advised.getTarget(), args);
        }
        if (AopUtils.isAspectAnnotation(advised.getTarget())) {
            return method.invoke(advised.getTarget(), args);
        }
        Object invoke = null;
        try {
            // 执行 before
            for (Advisor advisor : advised.getBeforeAdvisors(method, targetClass)) {
                advisor.getAdvice().invoke(args);
            }
            invoke = methodProxy.invokeSuper(o, args);
            // 执行 after
            for (Advisor advisor : advised.getAfterAdvisors(method,targetClass)) {
                advisor.getAdvice().invoke(args);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 执行 AfterThrowing
            for (Advisor advisor : advised.getAfterThrowingAdvisors(method,targetClass)) {
                advisor.getAdvice().invoke(args);
            }
        }
        return invoke;

    }
}
