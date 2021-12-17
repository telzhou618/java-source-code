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

    private List<MessageListener> messageListeners = new ArrayList<>(256);
    private final String topic;
    private NettyConfig nettyConfig;
    private ConsumerNettyHandler consumerNettyHandler;
    private NettyClient nettyClient;

    public DefaultConsumer(String topic) {
        this.topic = topic;
    }

    @Override
    public void start() {
        try {
            System.out.println("Consumer start...");
            // 初始化
            prepareConsumer();
            // 启动NettyClient
            nettyClient = new NettyClient(nettyConfig, consumerNettyHandler);
            nettyClient.start();
            Channel channel = nettyClient.getChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prepareConsumer() {
        this.nettyConfig = RemotingHelper.getDefaultNettyConfig();
        this.consumerNettyHandler = new ConsumerNettyHandler(messageListeners);
    }

    @Override
    public void registerMessageListener(MessageListener messageListener) {
        this.messageListeners.add(messageListener);
    }
}
