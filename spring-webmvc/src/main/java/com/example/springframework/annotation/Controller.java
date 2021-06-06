package com.example.springframework.annotation;

import java.lang.annotation.*;

/**
 * @author zhougaojun
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Controller {

    String value() default "";

}