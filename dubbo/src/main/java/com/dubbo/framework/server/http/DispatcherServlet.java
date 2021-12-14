package com.dubbo.framework.server.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author telzhou
 * @since 2021/6/11
 */
public class DispatcherServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 执行 handler 处理器
        HttpServerHandler httpServerHandler = new HttpServerHandler();
        httpServerHandler.handler(request, response);
    }
}
