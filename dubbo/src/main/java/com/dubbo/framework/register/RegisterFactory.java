package com.dubbo.framework.register;

/**
 * @author telzhou
 * @since 2021/6/21
 */
public class RegisterFactory {


    public static Register getRegister(String registerType) {

        switch (registerType) {
            case "zookeeper":
                return new ZookeeperRegister();
            case "redis":
                return new RedisRegister();
            case "local":
            default:
                return new LocalFileRegister();
        }
    }
}
