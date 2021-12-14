package com.example.aop.core;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author telzhou
 * @since 2021/12/7
 */
@Getter
@Setter
@Accessors(chain = true)
public class AdvisorInfo extends AdvisorSupport {

    public AdvisorInfo(Advice advice, String pointCutExpression) {
        super(advice, pointCutExpression);
    }
}
