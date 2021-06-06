package org.example.session;

import lombok.Getter;
import lombok.Setter;
import org.example.binding.MapperProxy;
import org.example.builder.annotation.MapperAnnotationBuilder;
import org.example.executor.Executor;
import org.example.executor.SimpleExecutor;
import org.example.executor.parameter.DefaultParameterHandler;
import org.example.executor.parameter.ParameterHandler;
import org.example.executor.resultset.DefaultResultSetHandler;
import org.example.executor.resultset.ResultSetHandler;
import org.example.executor.statement.SimpleStatementHandler;
import org.example.executor.statement.StatementHandler;
import org.example.mapping.Environment;
import org.example.mapping.MappedStatement;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhou1
 */
@Getter
@Setter
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
}
