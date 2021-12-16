package com.example.consumer;

/**
 * @author zhougaojun
 * @since 2021/12/16
 */
public interface Consumer {

    void start();

    void registerMessageListener(MessageListener messageListener);
}
