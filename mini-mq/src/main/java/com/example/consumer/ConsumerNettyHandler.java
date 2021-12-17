package com.example.consumer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * @author zhougaojun
 * @since 2021/12/16
 */
public class ConsumerNettyHandler extends SimpleChannelInboundHandler<String> {

    private List<MessageListener> messageListeners;

    public ConsumerNettyHandler(List<MessageListener> messageListeners) {
        this.messageListeners = messageListeners;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
        for (MessageListener messageListener : messageListeners) {
            messageListener.onConsumeMessage(message);
        }
    }
}
