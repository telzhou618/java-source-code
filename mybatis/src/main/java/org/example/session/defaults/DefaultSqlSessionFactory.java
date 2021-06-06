package org.example.session.defaults;

import org.example.executor.Executor;
import org.example.mapping.Environment;
import org.example.session.Configuration;
import org.example.session.SqlSession;
import org.example.session.SqlSessionFactory;

/**
 * @author zhougaojun
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
