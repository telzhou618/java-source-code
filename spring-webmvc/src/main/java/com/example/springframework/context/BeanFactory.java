package com.example.springframework.context;

import com.example.springframework.exception.BeansException;

import java.util.List;

/**
 * 管理和创建所有bean
 *
 * @author zhougaojun
 */
public interface BeanFactory {

    <T> T getBean(String name) throws BeansException;

    <T> T getBean(Class clazz) throws BeansException;

    List<String> getBeanNames();
}
