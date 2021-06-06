package com.example.springframework.annotation;

import java.lang.annotation.*;

/**
 * @author zhougaojun
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lazy {

    boolean value() default true;

}