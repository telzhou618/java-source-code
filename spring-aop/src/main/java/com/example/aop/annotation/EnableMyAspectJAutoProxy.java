package com.example.aop.annotation;

import com.example.aop.MyAspectJAutoProxyRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author telzhou
 * @since 2021/12/7
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MyAspectJAutoProxyRegistrar.class)
public @interface EnableMyAspectJAutoProxy {
}
