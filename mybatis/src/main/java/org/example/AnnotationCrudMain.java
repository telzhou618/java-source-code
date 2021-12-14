package org.example;

import org.apache.log4j.Logger;
import org.mybatis.io.Resources;
import org.mybatis.session.SqlSession;
import org.mybatis.session.SqlSessionFactory;
import org.mybatis.session.SqlSessionFactoryBuilder;
import org.example.mapper.BlogMapper;

import java.io.InputStream;

/**
 * @author telzhou
 */
public class AnnotationCrudMain {

    private static final Logger LOG = Logger.getLogger(XmlSqlCrudMain.class);

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

        LOG.info("查询1条："+blogMapper.selectBlogById(1));
        LOG.info("查询所有记录："+blogMapper.selectAllBlog());
        LOG.info("根据条件查询："+blogMapper.selectBlogByTitleAndContent("标题2", "内容2"));

        LOG.info("插入："+blogMapper.insertAnnoBlog("测试111", "测试111"));
        LOG.info("更新："+blogMapper.updateAnnoBlog("111", 8));
        LOG.info("删除："+blogMapper.deleteAnnoBlog(9));
    }
}
