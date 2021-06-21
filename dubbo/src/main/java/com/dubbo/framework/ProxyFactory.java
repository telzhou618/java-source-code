package com.dubbo.framework;

import com.dubbo.framework.register.Register;
import com.dubbo.framework.register.RegisterFactory;
import com.dubbo.framework.server.http.HttpProtocol;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author zhou1
 * @since 2021/6/16
 */
public class ProxyFactory {

    public static <T> T getProxyService(Class<T> tClass) {

        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class[]{tClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Invocation invocation = new Invocation(tClass.getName(), method.getName(), method.getParameterTypes(), args);

                Register register = RegisterFactory.getRegister("local");
                List<Url> urls = register.getService("user-service");

                Url url = LoadBalance.getRandom(urls);
                if (url == null) {
                    throw new RuntimeException("注册中心未查到服务");
                }
                Protocol protocol = new HttpProtocol();
                return protocol.send(url, invocation, method.getReturnType());
            }
        });
    }
}
