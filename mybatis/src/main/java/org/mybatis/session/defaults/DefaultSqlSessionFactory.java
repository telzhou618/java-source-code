package org.mybatis.session.defaults;

import org.mybatis.executor.Executor;
import org.mybatis.mapping.Environment;
import org.mybatis.session.Configuration;
import org.mybatis.session.SqlSession;
import org.mybatis.session.SqlSessionFactory;

/**
 * @author zhou1
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        try {
            final Environment environment = configuration.getEnvironment();
            //生成一个执行器(事务包含在执行器里)
            final Executor executor = configuration.newExecutor(environment.getDataSource());
            //然后产生一个DefaultSqlSession
            return new DefaultSqlSession(configuration, executor);
        } catch (Exception e) {
            throw new RuntimeException("Error opening session.  Cause: ", e);
        }
    }
}
