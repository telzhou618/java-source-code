package com.dubbo.framework.server.http;

import com.dubbo.framework.Invocation;
import com.dubbo.framework.Protocol;
import com.dubbo.framework.Url;

/**
 * @author zhou1
 * @since 2021/6/21
 */
public class HttpProtocol implements Protocol {
    @Override
    public void start(Url url) {
        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHostname(), url.getPort());
    }

    @Override
    public <T> T send(Url url, Invocation invocation, Class<T> returnType) {
        HttpClient httpClient = new HttpClient();
        String requestUrl = url.getScheme() + "://" + url.getHostname() + ":" + url.getPort();
        return httpClient.send(requestUrl, invocation, returnType);
    }
}
