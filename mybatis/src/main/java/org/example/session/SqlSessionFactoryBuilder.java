package org.example.session;

import org.example.builder.xml.XMLConfigBuilder;
import org.example.session.defaults.DefaultSqlSessionFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhougaojun
 */
public class SqlSessionFactoryBuilder {

    /**
     * 解析 XML、实例化SqlSession 工厂
     */
    public SqlSessionFactory build(InputStream inputStream) {
        try {
            XMLConfigBuilder parser = new XMLConfigBuilder(inputStream);
            Configuration configuration = parser.parse();
            return new DefaultSqlSessionFactory(configuration);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
