package com.dubbo.framework.register;

import com.dubbo.framework.Url;

import java.util.List;

/**
 * @author telzhou
 * @since 2021/6/11
 */
public class ZookeeperRegister implements Register {


    @Override
    public void register(String serviceName, Url url) {

    }

    @Override
    public List<Url> getService(String serviceName) {
        return null;
    }
}
