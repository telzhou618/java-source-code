package com.example;

import com.example.service.impl.OrderService;
import com.example.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

/**
 * @author telzhou
 * @since 2021/12/14
 */
public class AspectExpressionTests {

    /**
     * 对类做匹配 返回匹配结果
     * @throws Exception
     */
    @Test
    public void testClassFilter() throws Exception {
        String expression = "execution(public * com.example.service.impl.*.*(..))";
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression(expression);
        boolean matches = aspectJExpressionPointcut.getClassFilter().matches(OrderService.class);
        Assert.assertTrue(matches);
    }

    /**
     * 对方法做匹配 返回匹配结果
     * @throws Exception
     */
    @Test
    public void testMethodInterceptor() throws Exception {
        String expression = "execution(public * com.example.service.impl.*.*(..))";
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression(expression);
        boolean matches = aspectJExpressionPointcut.getMethodMatcher().matches(UserServiceImpl.class.getDeclaredMethod("sayHello"), UserServiceImpl.class);
        Assert.assertTrue(matches);
    }
}
