package com.example.example;

import com.example.producer.DefaultProducer;
import com.example.producer.Producer;

/**
 * @author zhougaojun
 * @since 2021/12/16
 */
public class ExampleProducer {

    /**
     * 发送消息
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        Producer producer = new DefaultProducer("test_topic");
        producer.start();
        producer.send("hello world");
        producer.shutdown();
    }
}
