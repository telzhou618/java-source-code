package com.dubbo.framework.server.http;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author telzhou
 * @since 2021/6/17
 */
public class ApplicationContext {

    private static Map<String, Object> registerMap = new LinkedHashMap<>(256);

    public static void register(String className, Object object) {
        registerMap.put(className, object);
    }

    public static Object get(String className) {
        return registerMap.get(className);
    }
}
