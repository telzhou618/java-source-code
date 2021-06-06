package org.example.executor.statement;

import org.apache.log4j.Logger;
import org.example.executor.parameter.ParameterHandler;
import org.example.executor.resultset.ResultSetHandler;
import org.example.mapping.MappedStatement;
import org.example.session.Configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhou1
 */
public class SimpleStatementHandler implements StatementHandler {

    private static final Logger LOG = Logger.getLogger(SimpleStatementHandler.class);

    private Configuration configuration;
    private MappedStatement mappedStatement;
    private String sql;
    private Object parameterObject;
    private ParameterHandler parameterHandler;
    private ResultSetHandler resultSetHandler;


    public SimpleStatementHandler(MappedStatement mappedStatement, Object parameterObject) {
        this.configuration = mappedStatement.getConfiguration();
        this.sql = mappedStatement.getSql();
        this.mappedStatement = mappedStatement;
        this.parameterObject = parameterObject;

        this.parameterHandler = configuration.newParameterHandler(mappedStatement, parameterObject);
        this.resultSetHandler = configuration.newResultSetHandler(mappedStatement, parameterHandler);

    }

    @Override
    public Statement prepare(Connection connection) throws SQLException {
        try {
            //实例化Statement
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error preparing statement.  Cause: " + e, e);
        }
    }

    @Override
    public void parameterize(Statement statement) throws SQLException {
        //调用ParameterHandler.setParameters
        parameterHandler.setParameters((PreparedStatement) statement);
    }

    @Override
    public <E> List<E> query(Statement statement) throws SQLException {
        LOG.info("Preparing : " + sql.trim());
        LOG.info("Parameters : " + getParameterValueString());
        PreparedStatement ps = (PreparedStatement) statement;
        ps.execute();
        // 结果集处理交给 resultSetHandler
        return resultSetHandler.<E>handleResultSets(statement);
    }

    @Override
    public int update(Statement statement) throws SQLException{
        LOG.info("Preparing : " + sql.trim());
        LOG.info("Parameters : " + getParameterValueString());
        //调用PreparedStatement.execute和PreparedStatement.getUpdateCount
        PreparedStatement ps = (PreparedStatement) statement;
        ps.execute();
        return ps.getUpdateCount();
    }

    protected String getParameterValueString() {
        if(parameterObject == null){
            return null;
        }
        List<Object> typeList = new LinkedList<>();
        if (parameterObject.getClass().isArray()) {
            Object[] arr = (Object[]) parameterObject;
            for (int i = 0; i < arr.length; i++) {
                typeList.add(arr[0] + "(" + arr[0].getClass().getName() + ")");
            }
        } else {
            typeList.add(parameterObject + "(" + parameterObject.getClass().getSimpleName() + ")");
        }
        final String parameters = typeList.toString();
        return parameters.substring(1, parameters.length() - 1);
    }
}
