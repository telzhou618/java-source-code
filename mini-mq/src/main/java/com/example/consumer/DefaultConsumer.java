package com.example.consumer;


import com.example.core.RemotingHelper;
import com.example.netty.NettyClient;
import com.example.netty.NettyConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhougaojun
 * @since 2021/12/16
 */
public class DefaultConsumer implements Consumer {

    private final List<MessageListener> messageListeners = new ArrayList<>(256);
    private final String topic;
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
            nettyClient.start();
            // 注册Consumer
            Channel channel = nettyClient.getChannel();
            channel.writeAndFlush(topic).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (!channelFuture.isSuccess()) {
                        System.out.println("Consumer 注册失败！");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prepareConsumer() {
        NettyConfig nettyConfig = RemotingHelper.getDefaultNettyConfig();
        ConsumerNettyHandler consumerNettyHandler = new ConsumerNettyHandler(messageListeners);
        this.nettyClient = new NettyClient(nettyConfig, consumerNettyHandler);
    }

    @Override
    public void registerMessageListener(MessageListener messageListener) {
        this.messageListeners.add(messageListener);
    }
}
