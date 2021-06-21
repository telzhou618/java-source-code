package com.dubbo.provider;

import com.dubbo.api.UserService;
import com.dubbo.framework.Protocol;
import com.dubbo.framework.Url;
import com.dubbo.framework.register.Register;
import com.dubbo.framework.register.RegisterFactory;
import com.dubbo.framework.server.http.ApplicationContext;
import com.dubbo.framework.server.http.HttpProtocol;
import com.dubbo.provider.impl.UserServiceImpl;

/**
 * @author zhou1
 * @since 2021/6/11
 */
public class Application2 {

    public static void main(String[] args) {
        //  1.注册 service
        Url url = new Url("http", "localhost", 8083);
        Register register = RegisterFactory.getRegister("local");
        register.register("user-service", url);

        ApplicationContext.register(UserService.class.getName(), new UserServiceImpl(url));
        //  2.启动 tomcat
        Protocol protocol = new HttpProtocol();
        protocol.start(url);

    }
}
