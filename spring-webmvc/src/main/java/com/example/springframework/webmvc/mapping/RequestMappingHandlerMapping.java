package com.example.springframework.webmvc.mapping;

import com.example.springframework.annotation.Controller;
import com.example.springframework.annotation.RequestMapping;
import com.example.springframework.context.AnnotationConfigApplicationContext;
import com.example.springframework.util.AnnotationUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhougaojun
 */
@Slf4j
public class RequestMappingHandlerMapping implements HandlerMapping {

    private final Map<String, HandlerMethod> handlerMap;

    public RequestMappingHandlerMapping(AnnotationConfigApplicationContext context) {
        handlerMap = new LinkedHashMap<>();
        for (String beanName : context.getBeanNames()) {
            Object bean = context.getBean(beanName);
            Class<?> beanClass = bean.getClass();
            if (AnnotationUtils.isAnnotationPresent(Controller.class, beanClass)) {
                String root = "";
                if (beanClass.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping annotation = beanClass.getAnnotation(RequestMapping.class);
                    root = annotation.value();
                }
                Method[] methods = beanClass.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        String uri = root + requestMapping.value();
                        HandlerMethod handlerMethod = new HandlerMethod(bean, beanClass, method);
                        handlerMap.put(uri, handlerMethod);
                        log.info("添加HandlerMapping:[{}],{}", uri, handlerMethod);
                    }
                }
            }
        }
    }

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {

        HandlerMethod handlerMethod = handlerMap.get(request.getRequestURI());
        if (handlerMethod != null) {
            return new HandlerExecutionChain(new HandlerMethod(handlerMethod));
        }
        return null;
    }
}
