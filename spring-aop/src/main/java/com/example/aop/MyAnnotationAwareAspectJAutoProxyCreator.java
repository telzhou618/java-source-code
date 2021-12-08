package com.example.aop;

import com.example.aop.annotation.*;
import com.example.aop.core.AdviceMetaData;
import com.example.aop.core.Advisor;
import com.example.aop.core.AdvisorMetaData;
import com.example.aop.core.PointCutMetaData;
import com.example.aop.proxy.ProxyFactory;
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
                // 解析 PointCut
                PointCutMetaData pointCutMetaData = Stream.of(ReflectionUtils.getDeclaredMethods(beanType))
                        .filter(m -> m.isAnnotationPresent(Pointcut.class))
                        .findFirst()
                        .map(m -> m.getAnnotation(Pointcut.class))
                        .map(a -> new PointCutMetaData(a.value()))
                        .orElse(null);

                // 解析 Advice
                Stream.of(ReflectionUtils.getDeclaredMethods(beanType))
                        .filter(m -> m.isAnnotationPresent(Before.class)
                                || m.isAnnotationPresent(After.class)
                                || m.isAnnotationPresent(AfterReturning.class)
                                || m.isAnnotationPresent(AfterThrowing.class)
                        ).forEach(m -> {

                    AdviceMetaData adviceMetaData = new AdviceMetaData()
                            .setAspectName(beanName)
                            .setAdviceMethodName(m.getName())
                            .setAdviceMethod(m);
                    // 存储Advisor
                    advisors.add(new AdvisorMetaData(adviceMetaData, pointCutMetaData));
                    aspectNameCache.add(beanName);
                });
            }
        }
        return advisors;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        // 获取指定类匹配的Advisor
        List<Advisor> advisors = getAdvicesAndAdvisorsForBean(bean.getClass(), beanName);
        return createProxy(bean, beanName, advisors);
    }

    private Object createProxy(Object bean, String beanName, List<Advisor> advisors) {

        ProxyFactory factory = new ProxyFactory();
        factory.setAdvisors(advisors);
        factory.setTarget(bean);
        return factory.getProxy();
    }

    private List<Advisor> getAdvicesAndAdvisorsForBean(Class<?> aClass, String beanName) {
        return advisorList;
    }
}
