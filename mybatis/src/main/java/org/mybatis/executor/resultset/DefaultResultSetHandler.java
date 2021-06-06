package org.mybatis.executor.resultset;

import org.mybatis.executor.parameter.ParameterHandler;
import org.mybatis.mapping.MappedStatement;
import org.mybatis.session.Configuration;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhou1
 */
public class DefaultResultSetHandler implements ResultSetHandler {

    private Configuration configuration;
    private MappedStatement mappedStatement;
    private ParameterHandler parameterHandler;


    public DefaultResultSetHandler(MappedStatement mappedStatement, ParameterHandler parameterHandler) {
        this.mappedStatement = mappedStatement;
        this.parameterHandler = parameterHandler;
        this.configuration = mappedStatement.getConfiguration();
    }

    @Override
    public <E> List<E> handleResultSets(Statement stmt) throws SQLException {
        List<Object> resultList = new LinkedList<>();
        ResultSet resultSet = null;
        try {
            resultSet = stmt.getResultSet();
            while (resultSet.next()) {
                Object o = handlerRow(resultSet, mappedStatement.getResultTypeClass());
                resultList.add(o);
            }
        } catch (Exception e) {
            throw new SQLException("ResultSet parse fail");
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return (List<E>) resultList;
    }

    /**
     * 转换一行数据
     */
    private Object handlerRow(ResultSet resultSet, Class<?> resultTypeClass) {
        try {
            Object o = resultTypeClass.newInstance();
            Field[] fields = resultTypeClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(o, resultSet.getObject(field.getName()));
            }
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
