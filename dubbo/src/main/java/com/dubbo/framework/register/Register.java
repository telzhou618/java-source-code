package com.dubbo.framework.register;

import com.dubbo.framework.Url;

import java.util.List;

/**
 * @author telzhou
 * @since 2021/6/21
 */
public interface Register {

    /**
     * 注册服务
     *
     * @param serviceName
     * @param url
     */
    void register(String serviceName, Url url);

    /**
     * 获取服务列表
     */
    List<Url> getService(String serviceName);
}
