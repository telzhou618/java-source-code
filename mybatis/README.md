# mybatis
手写 mybatis。
- 已实现对数据库的CRUD操作。
- 支持注解SQL和XML SQL两种方式。
- 引入druid作为测试数据源。
- mybatis 包是核心源码的实现。
- example 包是使用实例。
## Configure
Mybatis 配置类，所有配置信息在启动时会解析到配置类中。
## SqlSession
操作数据库的统一接口。
## executor
执行数据库、一级缓存、二级缓存的统一接口。
## StatementHandler
具体发生数据库连接，执行的接口。
## ParameterHandler
解析参数接口。
## ResultHandler
处理数据库返回结果，数据转换接口。