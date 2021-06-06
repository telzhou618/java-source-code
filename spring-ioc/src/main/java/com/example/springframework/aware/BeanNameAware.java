package com.example.springframework.aware;

/**
 * @author zhougaojun
 */
public interface BeanNameAware extends Aware {

    void setBeanName(String name);
}
