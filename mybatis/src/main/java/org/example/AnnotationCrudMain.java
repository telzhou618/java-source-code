package org.example;

import org.example.io.Resources;
import org.example.session.SqlSession;
import org.example.session.SqlSessionFactory;
import org.example.session.SqlSessionFactoryBuilder;
import org.example.test.mapper.BlogMapper;

import java.io.InputStream;

/**
 * @author zhougaojun
 */
public class AnnotationCrudMain {

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
