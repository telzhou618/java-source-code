package com.example.service;

import com.example.springframework.FactoryBean;
import com.example.springframework.annotation.Scope;
import com.example.springframework.annotation.Service;
import com.example.springframework.aware.BeanNameAware;

/**
 * @author zhougaojun
 */
@Service("userService")
@Scope("prototype")
public class UserService implements FactoryBean, BeanNameAware {

    private String beanName;

    @Override
    public Object getObject() throws Exception {
        return new UserService();
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void print() {
        System.out.println("beanName = " + beanName);
    }
}
