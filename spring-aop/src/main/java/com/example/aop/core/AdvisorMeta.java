package com.example.aop.core;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author zhougaojun
 * @since 2021/12/7
 */
@Getter
@Setter
@Accessors(chain = true)
public class AdvisorMeta extends AdvisorSupport {

    public AdvisorMeta(Advice advice, String pointCutExpression) {
        super(advice, pointCutExpression);
    }
}
