package org.mybatis.executor.parameter;

import org.mybatis.mapping.MappedStatement;
import org.mybatis.session.Configuration;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author telzhou
 */
public class DefaultParameterHandler implements ParameterHandler {

    private MappedStatement mappedStatement;
    private Configuration configuration;
    private Object parameterObject;

    @Override
    public Object getParameterObject() {
        return parameterObject;
    }

    @Override
    public void setParameters(PreparedStatement ps) throws SQLException {
        if (parameterObject != null) {
            if (parameterObject.getClass().isArray()) {
                Object[] arr = (Object[]) parameterObject;
                for (int i = 0; i < arr.length; i++) {
                    ps.setObject(i + 1, arr[i]);
                }
            } else {
                ps.setObject(1, parameterObject);
            }
        }

    }

    public DefaultParameterHandler(MappedStatement mappedStatement, Object parameterObject) {
        this.mappedStatement = mappedStatement;
        this.parameterObject = parameterObject;
        this.configuration = mappedStatement.getConfiguration();
    }
}
