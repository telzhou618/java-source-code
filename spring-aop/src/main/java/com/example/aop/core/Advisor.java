package com.example.aop.core;


/**
 * @author telzhou
 * @since 2021/12/7
 */
public interface Advisor extends PointCutMatcher {

    /**
     * 切面
     * @return
     */
    Advice getAdvice();

    /**
     * 切点表达式
     * @return
     */
    String getPointCutExpression();

}
