package com.example.springframework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhougaojun
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {

    /**
     * String SCOPE_SINGLETON = "singleton";
     * String SCOPE_PROTOTYPE = "prototype";
     */
    String value() default "";

}