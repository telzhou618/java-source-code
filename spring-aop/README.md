# spring-aop
手写 spring aop

## 步骤
- 扫描切面, MyAnnotationAwareAspectJAutoProxyCreator 完成。
- 创建动态代理,  CglibAopProxy 和  JdkDynamicAopProxy 完成。

## 测试

- OrderServiceTest#getOrderId 普通类走cglib代理。
- UserServiceImplTest#sayHello 实现接口的类走JDK动态代理。