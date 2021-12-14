package com.example.aop.core;


/**
 * @author zhougaojun
 * @since 2021/12/7
 */
public interface Advisor extends Matcher{

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
