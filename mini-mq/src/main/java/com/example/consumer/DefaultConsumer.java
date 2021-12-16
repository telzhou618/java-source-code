package com.example.consumer;


import com.example.core.RemotingHelper;
import com.example.netty.NettyClient;
import com.example.netty.NettyConfig;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhougaojun
 * @since 2021/12/16
 */
public class DefaultConsumer implements Consumer {

    List<MessageListener> messageListeners = new ArrayList<>(256);
    private NettyConfig nettyConfig = RemotingHelper.getDefaultNettyConfig();
    private final String topic;
    private NettyClient nettyClient;

    public DefaultConsumer(String topic) {
        this.topic = topic;
    }

    @Override
    public void start() {
        try {
            System.out.println("Consumer start...");
            nettyClient = new NettyClient(nettyConfig);
            nettyClient.start();
            Channel channel = nettyClient.getChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerMessageListener(MessageListener messageListener) {
        this.messageListeners.add(messageListener);
    }
}
