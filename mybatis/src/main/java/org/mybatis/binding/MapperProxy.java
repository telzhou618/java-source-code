package org.mybatis.binding;

import org.mybatis.mapping.MappedStatement;
import org.mybatis.mapping.SqlCommandType;
import org.mybatis.session.Configuration;
import org.mybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author telzhou
 */
public class MapperProxy<T> implements InvocationHandler {

    private final Configuration configuration;
    private final SqlSession sqlSession;
    private final Class<?> mapperInterface;


    public MapperProxy(Configuration configuration, SqlSession sqlSession, Class<?> mapperInterface) {

        this.configuration = configuration;
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 继承自object类的方法无需代理
        if (Object.class.equals(method.getDeclaringClass())) {
            try {
                return method.invoke(this, args);
            } catch (Throwable t) {
                throw new RuntimeException();
            }
        }
        //执行
        String stmt = mapperInterface.getName() + "." + method.getName();
        MappedStatement mappedStatement = configuration.getMappedStatement(stmt);
        if (mappedStatement == null) {
            throw new RuntimeException("未查到SQL映射关系" + stmt);
        }
        // 根据不同的SQL类型执行SQL语句
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        switch (sqlCommandType) {
            case INSERT:
                return sqlSession.insert(stmt, args);
            case UPDATE:
                return sqlSession.update(stmt, args);
            case DELETE:
                return sqlSession.delete(stmt, args);
            case SELECT:
                // 如果返回值是集合或数组，就调用selectList,其他调selectOne
                if (Collection.class.isAssignableFrom(method.getReturnType())
                        || method.getReturnType().isArray()) {
                    return sqlSession.selectList(stmt, args);
                } else {
                    return sqlSession.selectOne(stmt, args);
                }
            default:
                throw new RuntimeException("非法的 sqlCommandType," + sqlCommandType);
        }
    }
}
