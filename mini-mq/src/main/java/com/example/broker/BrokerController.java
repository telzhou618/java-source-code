package com.example.broker;

/**
 * @author zhougaojun
 * @since 2021/12/16
 */
public class BrokerController {

    public void start() {
        Broker broker = new DefaultBroker();
        broker.start();
    }
}
