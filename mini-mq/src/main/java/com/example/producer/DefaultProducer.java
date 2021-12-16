package com.example.producer;


import com.example.core.RemotingHelper;
import com.example.netty.NettyClient;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * @author zhougaojun
 * @since 2021/12/16
 */
public class DefaultProducer implements Producer {

    private String topic;
    private NettyClient nettyClient;
    private Channel channel;

    public DefaultProducer(String topic) {
        this.topic = topic;
    }

    @Override
    public void start() {
        // 启动NettyClient
        nettyClient = new NettyClient(RemotingHelper.getDefaultNettyConfig());
        nettyClient.start();
        System.out.println("producer start...");
    }

    @Override
    public void send(String message) throws Exception {
        if (channel == null) {
            channel = nettyClient.getChannel();
        }
        channel.writeAndFlush(message).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture f) throws Exception {
                if (!f.isSuccess()) {
                    System.out.println("发送消息失败");
                }
            }
        });
        System.out.println("发送消息完成：" + message);
    }
}
