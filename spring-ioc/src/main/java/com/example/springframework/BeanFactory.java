package com.example.springframework;

/**
 * 管理和创建所有bean
 *
 * @author zhougaojun
 */
public interface BeanFactory {

    <T> T getBean(String name) throws BeansException;
}
