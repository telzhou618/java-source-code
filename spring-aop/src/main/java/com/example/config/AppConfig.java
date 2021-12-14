package com.example.config;

import com.example.aop.annotation.EnableMyAspectJAutoProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author telzhou
 * @since 2021/12/14
 */
@ComponentScan(basePackages = "com.example")
@EnableMyAspectJAutoProxy
public class AppConfig {
}
