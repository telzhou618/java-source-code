package com.example.producer;

/**
 * @author zhougaojun
 * @since 2021/12/16
 */
public interface Producer {

    void start();

    void shutdown();

    void send(String message) throws Exception;
}
