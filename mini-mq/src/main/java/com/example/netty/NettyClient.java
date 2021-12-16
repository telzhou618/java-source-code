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
import lombok.Setter;

/**
 * @author zhougaojun
 * @since 2021/12/16
 */
@Getter
public class NettyClient {

    EventLoopGroup group = new NioEventLoopGroup(10);
    private NettyConfig nettyConfig;
    private Bootstrap bootstrap;

    public NettyClient(NettyConfig nettyConfig) {
        this.nettyConfig = nettyConfig;
    }

    public void start() {
        //客户端需要一个事件循环组
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(group)
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
                            pipeline.addLast(new MessageNettyHandler());
                        }
                    });
           // System.out.println("netty client start");
            //启动客户端去连接服务器端
            //   ChannelFuture channelFuture = bootstrap.connect(nettyConfig.getHost(), nettyConfig.getPort()).sync();
            //对关闭通道进行监听
            //   channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            group.shutdownGracefully();
        }

    }

    public Channel getChannel() throws InterruptedException {
        ChannelFuture channelFuture = this.bootstrap.connect(RemotingHelper.toSocketAddress(nettyConfig)).sync();
        return channelFuture.channel();
    }
}
