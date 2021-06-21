package com.dubbo.framework;

/**
 * @author zhou1
 * @since 2021/6/21
 */
public interface Protocol {

    void start(Url url);

    <T> T send(Url url, Invocation invocation, Class<T> returnType);
}
