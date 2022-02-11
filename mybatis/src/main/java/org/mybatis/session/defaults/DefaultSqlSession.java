package org.mybatis.session.defaults;

import org.mybatis.executor.ErrorContext;
import org.mybatis.executor.Executor;
import org.mybatis.mapping.MappedStatement;
import org.mybatis.session.Configuration;
import org.mybatis.session.SqlSession;

import java.util.List;

/**
 * @author telzhou
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    public <T> List<T> selectList(String statement, Object parameter) {
        try {
            //根据statement id找到对应的MappedStatement
            MappedStatement ms = configuration.getMappedStatement(statement);
            if (ms == null) {
                throw new RuntimeException("未找到SQL映射 " + statement);
            }
            //转而用执行器来查询结果,注意这里传入的ResultHandler是null
            return executor.query(ms, parameter);
        } catch (Exception e) {
            throw new RuntimeException(" SQL 执行异常 " + e, e);
        }
    }

    @Override
    public <T> T selectOne(String statement, Object... parameter) {
        List<T> list = this.<T>selectList(statement, parameter);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new RuntimeException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        } else {
            return null;
        }
    }

    @Override
    public int insert(String statement, Object parameter) {
        return this.update(statement, parameter);
    }

    @Override
    public int delete(String statement, Object parameter) {
        return this.update(statement, parameter);
    }

    @Override
    public int update(String statement, Object parameter) {
        try {
            MappedStatement ms = configuration.getMappedStatement(statement);
            if (ms == null) {
                throw new RuntimeException("未找到SQL映射 " + statement);
            }
            //转而用执行器来update结果
            return executor.update(ms, parameter);
        } catch (Exception e) {
            throw new RuntimeException(" SQL 执行异常" + e, e);
        }
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.<T>getMapper(type, this);
    }
}
