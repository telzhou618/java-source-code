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
## 实例
### 解析XML实例
```java
 public class XmlSqlCrudMain {

    /**
     * 解析XML方式实例
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();

        Blog blog = session.selectOne("org.example.mapper.BlogMapper.selectBlog", 1);
        System.out.println("查询 blog：" + blog);

        List<Blog> blogs = session.selectList("org.example.mapper.BlogMapper.selectAllBlog",null);
        System.out.println("查询所有 blogs：" + blogs);

        int insert = session.insert("org.example.mapper.BlogMapper.insertBlog", new String[]{"标题2", "内容2"});
        System.out.println("新增 blog：" + insert);

        int update = session.update("org.example.mapper.BlogMapper.updateBlog", new Object[]{"标题xxx", 1});
        System.out.println("更新 blog：" + update);

        int delete = session.delete("org.example.mapper.BlogMapper.deleteBlog", 4);
        System.out.println("删除 blog：" + delete);
    }
}
```
### 解析注解实例
```java
public class AnnotationCrudMain {

    /**
     * 解析注解方式实例
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        BlogMapper blogMapper = session.getMapper(BlogMapper.class);

        System.out.println(blogMapper.selectBlogById(1));
        System.out.println(blogMapper.selectAllBlog());
        System.out.println(blogMapper.selectBlogByTitleAndContent("标题2", "内容2"));

        System.out.println(blogMapper.insertAnnoBlog("测试111", "测试111"));
        System.out.println(blogMapper.updateAnnoBlog("111", 8));
        System.out.println(blogMapper.deleteAnnoBlog(9));
    }
}
```