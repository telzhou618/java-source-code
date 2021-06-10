package com.example.springframework.context;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** bean 定义
 * @author zhougaojun
 */
@Getter
@Setter
@ToString
public class BeanDefinition {

    private Class<?> beanClass;  // BeanDefinition
}
