package com.example.aop.core;


/**
 * @author zhougaojun
 * @since 2021/12/7
 */
public interface Advisor {

    Advice getAdvice();

    PointCut getPointCut();
}
