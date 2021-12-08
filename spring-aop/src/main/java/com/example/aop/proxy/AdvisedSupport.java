package com.example.aop.proxy;

import com.example.aop.core.Advisor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
}
