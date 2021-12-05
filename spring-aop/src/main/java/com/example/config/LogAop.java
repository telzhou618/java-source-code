package com.example.config;

import com.example.anno.After;
import com.example.anno.Aspect;
import com.example.anno.Before;
import org.springframework.stereotype.Component;

/**
 * @author jameszhou
 */
@Component
@Aspect
public class LogAop {

    @Before
    public void beforeLog() {
        System.out.println("业务执行前记录日志！");
    }


    @After
    public void afterLog() {
        System.out.println("业务执行后记录日志！");
    }
}
