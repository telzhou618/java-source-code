package org.mybatis.session;

import org.mybatis.binding.MapperProxy;
import org.mybatis.builder.annotation.MapperAnnotationBuilder;
import org.mybatis.executor.Executor;
import org.mybatis.executor.SimpleExecutor;
import org.mybatis.executor.parameter.DefaultParameterHandler;
import org.mybatis.executor.parameter.ParameterHandler;
import org.mybatis.executor.resultset.DefaultResultSetHandler;
import org.mybatis.executor.resultset.ResultSetHandler;
import org.mybatis.executor.statement.SimpleStatementHandler;
import org.mybatis.executor.statement.StatementHandler;
import org.mybatis.mapping.Environment;
import org.mybatis.mapping.MappedStatement;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author telzhou
 */
public class Configuration {

    private Environment environment;
    //映射的语句,存在Map里
    private final Map<String, MappedStatement> mappedStatements = new HashMap<>();
    // Mapper接口mapper
    private final Map<Class<?>, Class<?>> knownMappers = new HashMap<>();


    public Executor newExecutor(DataSource dataSource) {
        return new SimpleExecutor(this, dataSource);
    }

    public MappedStatement getMappedStatement(String id) {
        return mappedStatements.get(id);
    }

    public void addMappedStatement(MappedStatement ms) {
        mappedStatements.put(ms.getId(), ms);
    }

    public StatementHandler newStatementHandler(MappedStatement ms, Object parameter) {
        return new SimpleStatementHandler(ms, parameter);
    }

    public ParameterHandler newParameterHandler(MappedStatement mappedStatement, Object parameterObject) {
        return new DefaultParameterHandler(mappedStatement, parameterObject);
    }

    public ResultSetHandler newResultSetHandler(MappedStatement mappedStatement, ParameterHandler parameterHandler) {
        return new DefaultResultSetHandler(mappedStatement, parameterHandler);
    }

    public <T> T getMapper(Class<T> type, SqlSession session) {
        Class<?> mapperInterface = knownMappers.get(type);
        MapperProxy<?> mapperProxy = new MapperProxy<>(this, session, mapperInterface);
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }


    public <T> void addMapper(Class<T> type) {
        knownMappers.put(type, type);
        MapperAnnotationBuilder parser = new MapperAnnotationBuilder(this, type);
        parser.parse();
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Map<String, MappedStatement> getMappedStatements() {
        return mappedStatements;
    }

    public Map<Class<?>, Class<?>> getKnownMappers() {
        return knownMappers;
    }
}
