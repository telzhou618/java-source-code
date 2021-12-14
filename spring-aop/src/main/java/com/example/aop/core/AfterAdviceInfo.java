package com.example.aop.core;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.BeanFactory;

import java.lang.reflect.Method;

/**
 * @author telzhou
 * @since 2021/12/7
 */
@Getter
@Setter
@Accessors(chain = true)
public class AfterAdviceInfo extends AdviceInfo {

    public AfterAdviceInfo(String aspectName, Method method, BeanFactory beanFactory) {
        super(aspectName, method, beanFactory);
    }
}
