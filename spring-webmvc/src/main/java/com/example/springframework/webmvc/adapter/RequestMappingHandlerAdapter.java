package com.example.springframework.webmvc.adapter;

import com.alibaba.fastjson.JSON;
import com.example.springframework.annotation.ResponseBody;
import com.example.springframework.webmvc.mapping.HandlerMethod;
import com.example.springframework.webmvc.videw.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author zhougaojun
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerMethod;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Object object = handlerMethod.getBean();
        try {
            Object[] args = getMethodArgumentValues(request, method);
            Object returnValue = method.invoke(object, args);
            return handleReturnValue(returnValue, handlerMethod, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("代理方法执行异常", e);
        }
    }

    private Object[] getMethodArgumentValues(HttpServletRequest request, Method method) {

        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            args[i] = resolveArgument(request, parameter);
        }
        return args;
    }

    private Object resolveArgument(HttpServletRequest request, Parameter parameter) {
        if (parameter.getType().isAssignableFrom(Integer.class)) {
            return Integer.parseInt(request.getParameter(parameter.getName()));
        } else if (parameter.getType().isAssignableFrom(String.class)) {
            return request.getParameter(parameter.getName());
        }
        return null;
    }

    public ModelAndView handleReturnValue(Object returnValue, HandlerMethod handlerMethod, HttpServletResponse response) {
        // JSON View
        if (handlerMethod.getBeanType().isAnnotationPresent(ResponseBody.class)
                || handlerMethod.getMethod().isAnnotationPresent(ResponseBody.class)) {
            writeJson(response, returnValue);
            return null;
            // return new ModelAndView(new JsonView(), returnValue);
        }
        return (ModelAndView) returnValue;
    }

    private void writeJson(HttpServletResponse response, Object returnValue) {

        PrintWriter writer;
        try {
            // 返回JSON设置 ContentType，否则会显示乱码
            response.setContentType("application/json");
            writer = response.getWriter();
            writer.write(JSON.toJSONString(returnValue));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
