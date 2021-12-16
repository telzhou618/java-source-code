package com.example.core;

import com.example.netty.NettyConfig;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @author zhougaojun
 * @since 2021/12/16
 */
public class RemotingHelper {

    public static NettyConfig getDefaultNettyConfig() {
        return new NettyConfig("127.0.0.1", 9000);
    }

    public static SocketAddress toSocketAddress(NettyConfig config) {
        InetSocketAddress isa = new InetSocketAddress(config.getHost(), config.getPort());
        return isa;
    }
}
