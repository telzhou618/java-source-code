package com.example.example;

import com.example.broker.BrokerController;

/**
 * @author zhougaojun
 * @since 2021/12/17
 */
public class ExampleBrokerController {

    /**
     * 启动 broker
     * @param args
     */
    public static void main(String[] args) {
        BrokerController controller = new BrokerController();
        controller.start();
    }
}
