package com.example.broker;

import com.example.store.StoreService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author zhougaojun
 * @since 2021/12/16
 */
@ChannelHandler.Sharable
public class BrokerNettyServerHandler extends SimpleChannelInboundHandler<String> {

    // 存储Channel
    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    // 持久化
    private final StoreService storeService;

    public BrokerNettyServerHandler(StoreService storeService) {
        this.storeService = storeService;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        channelGroup.add(channel);
        System.out.println(ctx.channel().remoteAddress() + " 上线了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        System.out.println(ctx.channel().remoteAddress() + " 下线了");
    }

    //读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        //获取到当前 channel
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                // 转发消息给Consumer
                ch.writeAndFlush(msg);
            }
        });
        // 持久化消息
        storeService.storeMessage(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //关闭通道
        ctx.close();
    }

}
