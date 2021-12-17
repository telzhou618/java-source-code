package com.example.store.impl;

import com.example.store.StoreService;

/**
 * @author zhougaojun
 * @since 2021/12/17
 */
public class StoreServiceImpl implements StoreService {
    @Override
    public void storeMessage(String message) {
        System.out.println("持久化消息:" + message);
    }
}
