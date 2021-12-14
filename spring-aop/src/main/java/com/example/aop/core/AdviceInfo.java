package com.example.aop.core;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.BeanFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zhougaojun
 * @since 2021/12/7
 */
@Getter
@Setter
@Accessors(chain = true)
public abstract class AdviceInfo implements Advice {

    private String aspectName;
    private Method method;
    private BeanFactory beanFactory;

    public AdviceInfo(String aspectName, Method method, BeanFactory beanFactory) {
        this.aspectName = aspectName;
        this.method = method;
        this.beanFactory = beanFactory;
    }

    @Override
    public void invoke(Object[] args) throws InvocationTargetException, IllegalAccessException {
        method.invoke(beanFactory.getBean(aspectName), args);
    }
}
