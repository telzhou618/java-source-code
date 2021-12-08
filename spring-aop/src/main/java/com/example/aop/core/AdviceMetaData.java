package com.example.aop.core;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.lang.reflect.Method;

/**
 * @author zhougaojun
 * @since 2021/12/7
 */
@Getter
@Setter
@Accessors(chain = true)
public class AdviceMetaData implements Advice{

    private String adviceMethodName;
    private Method adviceMethod;
    private String aspectName;

    public AdviceMetaData() {


    }

    @Override
    public void invoke(Object[] args) {
        System.out.println("执行切面方法！");
//        try {
//            adviceMethod.invoke(aspectObjet);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
    }
}
