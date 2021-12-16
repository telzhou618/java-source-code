package com.example.example;

import com.example.consumer.Consumer;
import com.example.consumer.DefaultConsumer;
import com.example.consumer.MessageListener;

/**
 * @author zhougaojun
 * @since 2021/12/16
 */
public class ExampleConsumer {

    public static void main(String[] args) {

        Consumer consumer = new DefaultConsumer("test_topic");
        consumer.registerMessageListener(new MessageListener() {
            @Override
            public void onConsumeMessage(String message) {
                System.out.println("收到消息:" + message);
            }
        });
        consumer.start();
    }
}
