package com.example.netty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @author zhougaojun
 * @since 2021/12/16
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NettyConfig {

    private String host;
    private int port;


}
