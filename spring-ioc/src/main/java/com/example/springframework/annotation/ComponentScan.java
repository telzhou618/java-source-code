package com.example.springframework.annotation;

import java.lang.annotation.*;

/**
 * @author zhougaojun
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ComponentScan {

    String value();
}
