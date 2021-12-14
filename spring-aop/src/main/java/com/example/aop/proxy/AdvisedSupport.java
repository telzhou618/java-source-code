package com.example.aop.proxy;

import com.example.aop.core.Advisor;
import com.example.aop.util.AopUtils;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhougaojun
 * @since 2021/12/7
 */
@Getter
@Setter
public class AdvisedSupport {

    /**
     * 候选切面
     */
    private List<Advisor> advisors;
    /**
     * 代理目标
     */
    private Object target;

    /**
     * 获取 before 通知根据，方法精选 + 只筛选BeforeAdviceInfo
     *
     * @param method
     * @param targetClass
     * @return
     */
    public List<Advisor> getBeforeAdvisors(Method method, Class<?> targetClass) {
        return getAdvisorsByMethod(method, targetClass).stream().filter(advisor -> AopUtils.isBeforeAdvice(advisor.getAdvice())).collect(Collectors.toList());
    }

    public List<Advisor> getAfterAdvisors(Method method, Class<?> targetClass) {
        return getAdvisorsByMethod(method, targetClass).stream().filter(advisor -> AopUtils.isAfterAdvice(advisor.getAdvice())).collect(Collectors.toList());
    }

    public List<Advisor> getAfterThrowingAdvisors(Method method, Class<?> targetClass) {
        return getAdvisorsByMethod(method, targetClass).stream().filter(advisor -> AopUtils.isAfterThrowingAdvice(advisor.getAdvice())).collect(Collectors.toList());
    }

    /**
     * 根据方法筛选
     *
     * @param method
     * @param targetClass
     * @return
     */
    private List<Advisor> getAdvisorsByMethod(Method method, Class<?> targetClass) {
        return this.advisors.stream().filter(advisor -> advisor.matchMethod(method, targetClass)).collect(Collectors.toList());
    }
}
