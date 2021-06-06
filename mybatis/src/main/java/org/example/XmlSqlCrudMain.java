package org.example;

import org.mybatis.io.Resources;
import org.mybatis.session.SqlSession;
import org.mybatis.session.SqlSessionFactory;
import org.mybatis.session.SqlSessionFactoryBuilder;
import org.example.entity.Blog;

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
