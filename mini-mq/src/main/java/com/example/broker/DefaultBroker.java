package com.example.broker;

import com.example.core.RemotingHelper;
import com.example.netty.NettyConfig;
import com.example.netty.NettyServer;
import com.example.store.impl.StoreServiceImpl;
import io.netty.channel.ChannelHandler;

/**
 * @author zhougaojun
 * @since 2021/12/16
 */
public class DefaultBroker implements Broker {

    private NettyConfig nettyConfig;
    private ChannelHandler channelHandler;
    private NettyServer nettyServer;

    @Override
    public void start() {
        System.out.println("broker start...");
        // 初始化
        prepareBroker();
        // 启动nettyServer
        nettyServer = new NettyServer(nettyConfig, channelHandler);
        nettyServer.start();

    }

    private void prepareBroker() {
        this.nettyConfig = RemotingHelper.getDefaultNettyConfig();
        this.channelHandler = new BrokerNettyServerHandler(new StoreServiceImpl());
    }

    @Override
    public void shutdown() {
        if (nettyServer != null) {
            nettyServer.shutdown();
        }
    }
}
