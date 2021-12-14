package com.example.aop;

import com.example.aop.annotation.After;
import com.example.aop.annotation.AfterThrowing;
import com.example.aop.annotation.Aspect;
import com.example.aop.annotation.Before;
import com.example.aop.core.*;
import com.example.aop.proxy.ProxyFactory;
import com.example.aop.util.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author telzhou
 */
public class MyAnnotationAwareAspectJAutoProxyCreator extends MyAbstractAutoProxyCreator {


    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        // 解析切面，存入缓存
        buildAspectJAdvisors();
        return null;
    }


    private void buildAspectJAdvisors() {

        // 得到容器中所以的beanName
        String[] beanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(
                this.beanFactory, Object.class, true, false);

        // 循环解析
        for (String beanName : beanNames) {
            if (aspectNameCache.contains(beanName)) {
                continue;
            }
            Class<?> beanType = this.beanFactory.getType(beanName);
            assert beanType != null;
            if (beanType.isAnnotationPresent(Aspect.class)) {

                // 解析切点表达式
                String pointCutExpression = AopUtils.getPointCutExpression(beanType);
                // 解析 Advice
                Method[] methods = ReflectionUtils.getDeclaredMethods(beanType);
                for (Method method : methods) {
                    // 非Advice方法忽略
                    if (!AopUtils.isAdviceMethod(method)) {
                        continue;
                    }
                    Advice advice = null;
                    if (method.isAnnotationPresent(Before.class)) {
                        advice = new BeforeAdviceInfo(beanName, method, beanFactory);
                    } else if (method.isAnnotationPresent(After.class)) {
                        advice = new AfterAdviceInfo(beanName, method, beanFactory);
                    } else if (method.isAnnotationPresent(AfterThrowing.class)) {
                        advice = new AfterThrowingAdviceInfo(beanName, method, beanFactory);
                    }
                    if (advice != null) {
                        advisorList.add(new AdvisorInfo(advice, pointCutExpression));
                        aspectNameCache.add(beanName);
                    }
                }
            }
        }
    }


    /**
     * 创建动态代理, bean初始化后执行
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 获取指定类匹配的Advisor
        List<Advisor> advisorForBeans = new ArrayList<>();
        for (Advisor advisor : advisorList) {
            // 初步筛选出和 beanClass 匹配的advisor
            if (advisor.matchClass(bean.getClass())) {
                advisorForBeans.add(advisor);
            }
        }
        // 创建动态代理
        if (!advisorForBeans.isEmpty()) {
            ProxyFactory factory = new ProxyFactory();
            factory.setAdvisors(advisorForBeans);
            factory.setTarget(bean);
            return factory.getProxy();
        }
        return bean;
    }
}
