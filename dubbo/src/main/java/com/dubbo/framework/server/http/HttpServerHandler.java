package com.dubbo.framework.server.http;

import com.alibaba.fastjson.JSON;
import com.dubbo.framework.Invocation;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zhou1
 * @since 2021/6/21
 */
public class HttpServerHandler {

    public void handler(HttpServletRequest request, HttpServletResponse response) {

        try {
            // fastjson 序列化
            byte[] bytes = IOUtils.toByteArray(request.getInputStream());
            Invocation invocation = JSON.parseObject(bytes, Invocation.class);

            Object serviceObject = ApplicationContext.get(invocation.getClassName());
            if (serviceObject == null) {
                throw new RuntimeException("服务提供者" + invocation.getClassName() + " 不存在!");
            }
            Class<?> serviceClass = serviceObject.getClass();
            Method method = serviceClass.getMethod(invocation.getMethodName(), invocation.getParamTypes());
            Object object = method.invoke(serviceObject, invocation.getParams());

            IOUtils.write(JSON.toJSONBytes(object), response.getOutputStream());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
