package com.example.aop.core;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

/**
 * @author zhougaojun
 * @since 2021/12/14
 */
public abstract class AdvisorSupport implements Advisor {

    private final Advice advice;
    private final String pointCutExpression;
    private final AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();

    public AdvisorSupport(Advice advice, String pointCutExpression) {
        this.advice = advice;
        this.pointCutExpression = pointCutExpression;
        aspectJExpressionPointcut.setExpression(pointCutExpression);
    }

    @Override
    public boolean matchClass(Class<?> clazz) {
        return aspectJExpressionPointcut.getClassFilter().matches(clazz);
    }

    @Override
    public boolean matchMethod(Method method, Class<?> targetClazz) {
        return aspectJExpressionPointcut.getMethodMatcher().matches(method, targetClazz);
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public String getPointCutExpression() {
        return this.pointCutExpression;
    }
}
