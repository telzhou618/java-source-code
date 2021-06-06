package com.example.springframework;


/**
 * 定义创建单个bean的方式
 *
 * @author zhougaojun
 */
public interface FactoryBean {

    Object getObject() throws Exception;
}
