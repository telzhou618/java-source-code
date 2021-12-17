package com.example.producer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author zhougaojun
 * @since 2021/12/16
 */
public class ProducerNettyHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
//        System.out.println(message);
    }
}
