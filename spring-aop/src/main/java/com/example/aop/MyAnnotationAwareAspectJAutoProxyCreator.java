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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author jameszhou
 */
public class MyAnnotationAwareAspectJAutoProxyCreator extends MyAbstractAutoProxyCreator {


    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        // 解析
        List<Advisor> advisors = buildAspectJAdvisors();
        advisorList.addAll(advisors);
        return null;
    }


    private List<Advisor> buildAspectJAdvisors() {

        List<Advisor> advisors = new ArrayList<>();
        // 得到容器中所以的beanName
        String[] beanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(
                this.beanFactory, Object.class, true, false);

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
                Stream.of(ReflectionUtils.getDeclaredMethods(beanType))
                        .filter(AopUtils::isAdviceMethod)
                        .forEach(m -> {
                            Advice advice = null;
                            if (m.isAnnotationPresent(Before.class)) {
                                advice = new BeforeAdviceInfo(beanName, m, beanFactory);
                            } else if (m.isAnnotationPresent(After.class)) {
                                advice = new AfterAdviceInfo(beanName, m, beanFactory);
                            } else if (m.isAnnotationPresent(AfterThrowing.class)) {
                                advice = new AfterThrowingAdviceInfo(beanName, m, beanFactory);
                            }
                            if (advice != null) {
                                advisors.add(new AdvisorMeta(advice, pointCutExpression));
                                aspectNameCache.add(beanName);
                            }
                        });
            }
        }
        return advisors;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        // 获取指定类匹配的Advisor
        List<Advisor> advisors = getAdvicesAndAdvisorsForBean(bean.getClass(), beanName);
        if (!advisors.isEmpty()) {
            return createProxy(bean, beanName, advisors);
        }
        return bean;
    }

    private Object createProxy(Object bean, String beanName, List<Advisor> advisors) {

        ProxyFactory factory = new ProxyFactory();
        factory.setAdvisors(advisors);
        factory.setTarget(bean);
        return factory.getProxy();
    }

    /**
     * 根据类名筛选出合适Advisor
     *
     * @param beanClass
     * @param beanName
     * @return
     */
    private List<Advisor> getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName) {

        List<Advisor> advisors = new ArrayList<>();

        for (Advisor advisor : advisorList) {
            // 初步筛选出和 beanClass 匹配的advisor
            if (advisor.matchClass(beanClass)) {
                advisors.add(advisor);
            }
        }
        return advisors;
    }
}
