package com.example.aop.util;

import com.example.aop.annotation.*;
import com.example.aop.core.Advice;
import com.example.aop.core.AfterAdviceInfo;
import com.example.aop.core.BeforeAdviceInfo;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.stream.Stream;

/**
 * @author zhougaojun
 * @since 2021/12/14
 */
public class AopUtils {
    public static boolean isCglibProxy(@Nullable Object object) {
        assert object != null;
        return object.getClass().getName().contains(ClassUtils.CGLIB_CLASS_SEPARATOR);
    }

    public static boolean isAspectAnnotation(@Nullable Object object) {
        assert object != null;
        return object.getClass().isAnnotationPresent(Aspect.class);
    }

    public static String getPointCutExpression(Class<?> beanClass) {
        return Stream.of(ReflectionUtils.getDeclaredMethods(beanClass))
                .filter(m -> m.isAnnotationPresent(Pointcut.class))
                .findFirst()
                .map(m -> m.getAnnotation(Pointcut.class))
                .map(Pointcut::value)
                .orElse(null);
    }

    public static boolean isAdviceMethod(Method method) {
        return method.isAnnotationPresent(Before.class)
                || method.isAnnotationPresent(After.class)
                || method.isAnnotationPresent(AfterReturning.class)
                || method.isAnnotationPresent(AfterThrowing.class);
    }

    public static boolean isBeforeAdvice(Advice advice) {
        return advice instanceof BeforeAdviceInfo;
    }

    public static boolean isAfterAdvice(Advice advice) {
        return advice instanceof AfterAdviceInfo;
    }

    public static boolean isAfterThrowingAdvice(Advice advice) {
        return advice instanceof AfterAdviceInfo;
    }
}
