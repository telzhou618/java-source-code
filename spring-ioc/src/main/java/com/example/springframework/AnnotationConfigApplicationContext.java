package com.example.springframework;

import com.example.springframework.annotation.*;
import com.example.springframework.aware.BeanNameAware;
import com.example.springframework.util.Resources;
import com.example.springframework.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * @author zhougaojun
 */
public class AnnotationConfigApplicationContext implements BeanFactory {
    /**
     * bean 定义存储 map
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>(256);
    /**
     * 单例 bean 存储 map
     */
    private final Map<String, Object> singletonsBeanMap = new HashMap<>(256);

    public AnnotationConfigApplicationContext(Class<?> appConfigClass) {
        // 扫描包,注入到bean定义map
        if (appConfigClass.isAnnotationPresent(ComponentScan.class)) {
            doScanner(appConfigClass.getAnnotation(ComponentScan.class).value());
        }
        // 创建单例 bean
        doCreateSingletonsBean();

        System.out.println("ioc 容器初始化完成!");
    }

    /**
     * 扫描包并且创建单例 bean
     */
    private void doScanner(String scanPackage) {
        if (StringUtil.isBlank(scanPackage)) {
            throw new BeansException("scanPackage path is not empty");
        }
        try {
            File[] files = Resources.getPackageAsFiles(scanPackage);
            for (File f : files) {
                // 根据文件路径获取类名
                String classPath = Optional.of(f.getAbsolutePath())
                        .map(path -> StringUtil.substringBetween(path, "classes/", ".class"))
                        .map(path -> path.replace("/", "."))
                        .orElse(null);
                // 加载类
                Class<?> aClass = Resources.loadClass(classPath);
                if (aClass.isAnnotationPresent(Service.class)) {
                    BeanDefinition beanDefinition = new BeanDefinition();
                    beanDefinition.setBeanClass(aClass);
                    // beanName 为空是取 class 简称首字母小写
                    String beanName = StringUtil.emptyToDefault(aClass.getAnnotation(Service.class).value(),
                            StringUtil.toLowerCaseFirstOne(aClass.getSimpleName()));
                    // 设置是否懒加载
                    if (aClass.isAnnotationPresent(Lazy.class)) {
                        beanDefinition.setLazyInit(true);
                    }
                    // 设置作用域
                    if (aClass.isAnnotationPresent(Scope.class)) {
                        String scopeValue = aClass.getAnnotation(Scope.class).value();
                        beanDefinition.setScope(scopeValue);
                    }
                    beanDefinitionMap.put(beanName, beanDefinition);
                    System.out.println("注入bean定义," + beanDefinition);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建非懒加载的单例bean
     */
    private void doCreateSingletonsBean() {
        Iterator<String> iterator = beanDefinitionMap.keySet().iterator();
        while (iterator.hasNext()) {
            String beanName = iterator.next();
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (!beanDefinition.isLazyInit()) {
                if (beanDefinition.getScope().equals("singleton")) {
                    Object bean = doCreateBean(beanDefinition, beanName);
                    singletonsBeanMap.put(beanName, bean);
                }
            }
        }
    }

    /**
     * 真正创建 bean 并返回
     */
    private Object doCreateBean(BeanDefinition beanDefinition, String beanName) {
        Object bean = null;
        try {
            Class<?> beanClass = beanDefinition.getBeanClass();
            bean = beanClass.newInstance();
            // FactoryBean
            if (bean instanceof FactoryBean) {
                bean = ((FactoryBean) bean).getObject();
            }
            // BeanNameAware
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 获取 bean
     */
    @Override
    public <T> T getBean(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException(beanName + "'s beanDefinition is not exist");
        }
        // 原型 bean 直接创建
        if (beanDefinition.getScope().equals("prototype")) {
            return (T) doCreateBean(beanDefinition, beanName);
        }
        // 单例 bean 先在单例池中找，找到了直接返回，没找到则创建单例bean,再放进单例池，再返回。
        Object bean = singletonsBeanMap.get(beanName);
        if (bean == null) {
            bean = doCreateBean(beanDefinition, beanName);
            singletonsBeanMap.put(beanName, bean);
        }
        return (T) bean;
    }
}
