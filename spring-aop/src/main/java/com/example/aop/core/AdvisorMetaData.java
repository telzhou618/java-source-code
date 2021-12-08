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
public class AdvisorMetaData implements Advisor {

    private AdviceMetaData adviceMetaData;
    private PointCutMetaData pointCutMetaData;

    public AdvisorMetaData(AdviceMetaData adviceMetaData, PointCutMetaData pointCutMetaData) {
        this.adviceMetaData = adviceMetaData;
        this.pointCutMetaData = pointCutMetaData;
    }

    @Override
    public Advice getAdvice() {
        return this.adviceMetaData;
    }

    @Override
    public PointCut getPointCut() {
        return pointCutMetaData;
    }
}
