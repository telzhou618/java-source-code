package com.example.netty;

import com.example.core.RemotingHelper;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.Getter;

/**
 * @author zhougaojun
 * @since 2021/12/16
 */
@Getter
public class NettyClient {

    private final EventLoopGroup eventLoopGroupWorker = new NioEventLoopGroup(10);
    private final Bootstrap bootstrap = new Bootstrap();


    private NettyConfig nettyConfig;
    private ChannelHandler channelHandler;


    public NettyClient(NettyConfig nettyConfig, ChannelHandler channelHandler) {
        this.nettyConfig = nettyConfig;
        this.channelHandler = channelHandler;
    }

    public void start() {
        //客户端需要一个事件循环组
        try {
            this.bootstrap.group(eventLoopGroupWorker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //pipeline.addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("_"
                            // .getBytes())));
                            //向pipeline加入解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            //向pipeline加入编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            //加入自己的业务处理handler
                            pipeline.addLast(channelHandler);
                        }
                    });
            // System.out.println("netty client start");
            //启动客户端去连接服务器端
            //   ChannelFuture channelFuture = bootstrap.connect(nettyConfig.getHost(), nettyConfig.getPort()).sync();
            //对关闭通道进行监听
            //   channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            eventLoopGroupWorker.shutdownGracefully();
        }

    }

    public Channel getChannel() throws InterruptedException {
        ChannelFuture channelFuture = this.bootstrap.connect(RemotingHelper.toSocketAddress(nettyConfig)).sync();
        return channelFuture.channel();
    }

    public void shutdown() {
        try {
            this.eventLoopGroupWorker.shutdownGracefully();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
