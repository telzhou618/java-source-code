package com.example.broker;

import com.example.core.RemotingHelper;
import com.example.netty.NettyConfig;
import com.example.netty.NettyServer;
import io.netty.channel.ChannelHandler;

/**
 * @author zhougaojun
 * @since 2021/12/16
 */
public class DefaultBroker implements Broker {

    private final NettyConfig nettyConfig = RemotingHelper.getDefaultNettyConfig();
    private final ChannelHandler channelHandler = new BrokerNettyServerHandler();

    private NettyServer nettyServer;

    @Override
    public void start() {

        System.out.println("broker start...");
        // 启动nettyServer
        nettyServer = new NettyServer(nettyConfig, channelHandler);
        nettyServer.start();

    }

    @Override
    public void shutdown() {
        if(nettyServer != null){
            nettyServer.shutdown();
        }
    }
}
