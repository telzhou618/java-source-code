package com.example.springframework.context;

import com.example.springframework.annotation.*;
import com.example.springframework.exception.BeansException;
import com.example.springframework.util.AnnotationUtils;
import com.example.springframework.util.Resources;
import com.example.springframework.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author zhougaojun
 */
@Slf4j
public class AnnotationConfigApplicationContext implements BeanFactory {
    private final String scanPackage;

    private final List<String> beanNames = new ArrayList<>(256);
    /**
     * bean 定义存储 map
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>(256);
    /**
     * 单例 bean 存储 map
     */
    private final Map<String, Object> singletonsBeanMap = new HashMap<>(256);

    public AnnotationConfigApplicationContext(String scanPackage) {
        // 扫描包,注入到bean定义map
        this.scanPackage = scanPackage;
    }

    public void onRefresh() {
        // 清理容器
        cleanContext();
        // 扫描包
        doScanner(this.scanPackage);
        // 创建单例 bean
        doCreateSingletonsBean();
    }

    private void cleanContext() {
        this.beanDefinitionMap.clear();
        this.singletonsBeanMap.clear();
    }

    /**
     * 扫描包,注册bean定义
     */
    private void doScanner(String scanPackage) {
        if (StringUtil.isBlank(scanPackage)) {
            throw new BeansException("scanPackage path is not empty");
        }
        try {
            File file = Resources.getPackageAsFile(scanPackage);
            // 使用common-io 递归获取指定目录下所有.class 文件
            Collection<File> files = FileUtils.listFiles(file, FileFilterUtils.suffixFileFilter("class"), DirectoryFileFilter.INSTANCE);
            for (File f : files) {
                // 根据文件路径获取类名
                String classPath = Optional.of(f.getAbsolutePath())
                        .map(path -> StringUtil.substringBetween(path, "classes/", ".class"))
                        .map(path -> path.replace("/", "."))
                        .orElse(null);
                // 加载类
                Class<?> aClass = Class.forName(classPath);
                if (AnnotationUtils.isAnyAnnotationPresent(new Class[]{Service.class, Controller.class}, aClass)) {

                    BeanDefinition beanDefinition = new BeanDefinition();
                    beanDefinition.setBeanClass(aClass);
                    // 设置 beanName
                    String beanName = "";
                    if (AnnotationUtils.isAnnotationPresent(Service.class, aClass)) {
                        Service service = AnnotationUtils.findAnnotation(Service.class, aClass);
                        beanName = service.value();
                    } else if (aClass.isAnnotationPresent(Controller.class)) {
                        Controller controller = AnnotationUtils.findAnnotation(Controller.class, aClass);
                        beanName = controller.value();
                    }
                    // beanName为空 取简称首字母小写
                    beanName = StringUtil.emptyToDefault(beanName, StringUtil.toLowerCaseFirstOne(aClass.getSimpleName()));

                    // 设置是否懒加载
                    if (AnnotationUtils.isAnnotationPresent(Lazy.class, aClass)) {
                        beanDefinition.setLazyInit(true);
                    }
                    // 设置作用域
                    if (AnnotationUtils.isAnnotationPresent(Scope.class, aClass)) {
                        Scope scope = AnnotationUtils.findAnnotation(Scope.class, aClass);
                        String scopeValue = scope.value();
                        if (StringUtil.isBlank(scopeValue)) {
                            beanDefinition.setScope(scopeValue);
                        }
                    }
                    beanNames.add(beanName);
                    beanDefinitionMap.put(beanName, beanDefinition);
                    log.info("注册bean定义,beanDefinition ={}", beanDefinition);
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
        for (String beanName : beanDefinitionMap.keySet()) {
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
            Field[] fields = beanClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object filedBean = getBean(field.getType());
                    if (filedBean != null) {
                        field.set(bean, filedBean);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("创建bean: bean = {}", bean);
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

    @Override
    public <T> T getBean(Class clazz) throws BeansException {
        Iterator<String> iterator = beanDefinitionMap.keySet().iterator();
        while (iterator.hasNext()) {
            String beanName = iterator.next();
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (clazz == beanDefinition.getBeanClass() ||
                    clazz.isAssignableFrom(beanDefinition.getBeanClass())) {
                return getBean(beanName);
            }
        }
        return null;
    }

    @Override
    public List<String> getBeanNames() {
        return this.beanNames;
    }
}
