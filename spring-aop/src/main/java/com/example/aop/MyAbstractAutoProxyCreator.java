package com.example.aop;

import com.example.aop.core.Advisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.core.Ordered;

import java.util.ArrayList;
import java.util.List;

/**
 * @author telzhou
 * @since 2021/12/7
 */
public abstract class MyAbstractAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware, Ordered {

    /**
     * 所有切面解析完成，保存在这里
     */
    protected static List<Advisor> advisorList = new ArrayList<>(256);

    /**
     * 记录 Aspect缓存，解析过，不在重复解析
     */
    protected static List<String> aspectNameCache = new ArrayList<>(256);

    protected ListableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        // AOP关键步骤一：实例化前解析 Advisor,由子类实现
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //AOP关键步骤二 初始化后生成动态代理，子类实现
        return bean;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        // 设置 bean 工厂，在Spring容器启动是实例化的是  ConfigurableListableBeanFactory
        // 在解析AOP配置时会用到
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public int getOrder() {
        // 加载顺序优先级最最好
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
