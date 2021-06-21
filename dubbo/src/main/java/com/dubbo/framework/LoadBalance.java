package com.dubbo.framework;

import java.util.List;
import java.util.Random;

/**
 * @author zhou1
 * @since 2021/6/21
 */
public class LoadBalance {

    /**
     * 负载均衡 - 随机
     *
     * @param list
     * @return
     */
    public static Url getRandom(List<Url> list) {
        Random random = new Random();
        int n = random.nextInt(list.size());
        return list.get(n);
    }
}
