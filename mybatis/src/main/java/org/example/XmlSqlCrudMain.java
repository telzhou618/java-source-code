package org.example;

import org.example.io.Resources;
import org.example.session.SqlSession;
import org.example.session.SqlSessionFactory;
import org.example.session.SqlSessionFactoryBuilder;
import org.example.test.entity.Blog;

import java.io.InputStream;
import java.util.List;

/**
 * @author zhou1
 */
public class XmlSqlCrudMain {

    public static void main(String[] args) throws Exception {

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();

        Blog blog = session.selectOne("org.example.test.mapper.BlogMapper.selectBlog", 1);
        System.out.println("查询 blog：" + blog);

        List<Blog> blogs = session.selectList("org.example.test.mapper.BlogMapper.selectAllBlog",null);
        System.out.println("查询所有 blogs：" + blogs);

        int insert = session.insert("org.example.test.mapper.BlogMapper.insertBlog", new String[]{"标题2", "内容2"});
        System.out.println("新增 blog：" + insert);

        int update = session.update("org.example.test.mapper.BlogMapper.updateBlog", new Object[]{"标题xxx", 1});
        System.out.println("更新 blog：" + update);

        int delete = session.delete("org.example.test.mapper.BlogMapper.deleteBlog", 4);
        System.out.println("删除 blog：" + delete);
    }
}
