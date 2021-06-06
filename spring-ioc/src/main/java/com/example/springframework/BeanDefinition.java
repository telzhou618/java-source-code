package com.example.springframework;

/**
 * @author zhougaojun
 */
public class BeanDefinition {

    private Class<?> beanClass;
    private String scope = "singleton";
    private boolean lazyInit = Boolean.FALSE;

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    @Override
    public String toString() {
        return "BeanDefinition{" +
                ", beanClass=" + beanClass +
                ", scope='" + scope + '\'' +
                ", lazyInit=" + lazyInit +
                '}';
    }
}
