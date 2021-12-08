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
public class PointCutMetaData implements PointCut{

    private String expression;

    public PointCutMetaData(String expression) {
        this.expression = expression;
    }

    /**
     * 节点表达式
     */
    public String getExpression(){
        return this.expression;
    }
}
