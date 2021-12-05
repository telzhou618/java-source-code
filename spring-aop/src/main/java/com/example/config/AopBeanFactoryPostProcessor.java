package com.example.config;

import com.example.anno.Aspect;
import com.example.anno.Before;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author jameszhou
 */
@Component
public class AopBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        String[] beanNames = beanFactory.getBeanNamesForAnnotation(Aspect.class);
        System.out.println(beanNames);

        for (String beanName : beanNames) {
            Class<?> aClass = beanFactory.getType(beanName);
            System.out.println();

            // 解析索引该类的方法，生成通知注入到spring容器
            Method[] methods = aClass.getDeclaredMethods();
            for (Method method : methods) {
                System.out.println(method.getName());
                Before annotation = method.getAnnotation(Before.class);
                if(annotation != null){
                    System.out.println("解析Before业务");
                    
                }
            }

        }

    }
}
