package com.example.consumer;

/**
 * @author zhougaojun
 * @since 2021/12/16
 */
public interface MessageListener {

    void onConsumeMessage(String message);
}
