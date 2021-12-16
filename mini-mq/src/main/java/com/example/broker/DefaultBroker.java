package com.example.broker;

import com.example.core.RemotingHelper;
import com.example.netty.NettyConfig;
import com.example.netty.NettyServer;

/**
 * @author zhougaojun
 * @since 2021/12/16
 */
public class DefaultBroker implements Broker {

    private NettyConfig nettyConfig = RemotingHelper.getDefaultNettyConfig();
    private NettyServer nettyServer;

    @Override
    public void start() {

        System.out.println("broker start...");
        // 启动nettyServer
        nettyServer = new NettyServer(nettyConfig);
        nettyServer.start();

    }
}
