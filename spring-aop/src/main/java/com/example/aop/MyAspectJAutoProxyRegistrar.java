package com.example.aop;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author zhougaojun
 * @since 2021/12/7
 */
public class MyAspectJAutoProxyRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 注册核心解析类 MyAnnotationAwareAspectJAutoProxyCreator.class
        RootBeanDefinition beanDefinition = new RootBeanDefinition(MyAnnotationAwareAspectJAutoProxyCreator.class);
        registry.registerBeanDefinition(MyAnnotationAwareAspectJAutoProxyCreator.class.getName(), beanDefinition);
        System.out.println("注册AOP解析器 MyAspectJAutoProxyRegistrar 成功！");
    }
}
