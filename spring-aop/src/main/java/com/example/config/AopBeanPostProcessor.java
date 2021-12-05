package com.example.config;

import com.example.proxy.CglibProxy;
import com.example.proxy.JdkDynamicProxy;
import com.example.service.UserServiceImpl;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author jameszhou
 */
@Component
public class AopBeanPostProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        // 判断哪些类需要生生成动态代理
        if (!bean.getClass().isAssignableFrom(UserServiceImpl.class)) {
            return bean;
        }
        return getProxy(bean, beanName);
    }

    private Object getProxy(Object bean, String beanName) {


        // 代理目标没有实现任何接口使用 cglib 动态代理
        if (bean.getClass().getInterfaces().length == 0) {

            // cglib 动态代理
            return new CglibProxy(bean.getClass(), new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

                    // 判断什么方法需要植入逻辑
                    if (method.getName().equalsIgnoreCase("sayHello")) {
                        System.out.println("cglib动态代理:业务执行前...");
                        Object result = methodProxy.invokeSuper(o, objects);
                        System.out.println("cglib动态代理:业务执行后...");
                        return result;
                    }
                    return methodProxy.invokeSuper(o, objects);
                }
            }).getProxy();
        } else {

            // JDK 动态代理
            return new JdkDynamicProxy(bean.getClass(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                    // 判断什么方法需要植入逻辑
                    if (method.getName().equalsIgnoreCase("sayHello")) {
                        System.out.println("jdk动态代理:业务执行前...");
                        Object re = method.invoke(bean, args);
                        System.out.println("jdk动态代理:业务执行后...");
                        return re;
                    }
                    return method.invoke(bean, args);
                }
            }).getProxy();
        }

    }
}
