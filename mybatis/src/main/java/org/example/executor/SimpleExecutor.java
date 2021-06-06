package org.example.executor;

import org.example.executor.statement.StatementHandler;
import org.example.mapping.MappedStatement;
import org.example.session.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author zhou1
 */
public class SimpleExecutor implements Executor {

    private Configuration configuration;
    private DataSource dataSource;

    public SimpleExecutor(Configuration configuration, DataSource dataSource) {
        this.configuration = configuration;
        this.dataSource = dataSource;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter) throws SQLException {
        Connection connection = null;
        Statement stmt = null;
        try {
            // 这个Configuration 和 BaseExecutor的Configuration是同一个对象
            Configuration configuration = ms.getConfiguration();
            //新建一个StatementHandler
            StatementHandler handler = configuration.newStatementHandler(ms, parameter);
            // 建立连接、准备语句
            connection = dataSource.getConnection();
            stmt = handler.prepare(connection);
            // 设置参数
            handler.parameterize(stmt);
            // 执行SQL语句, 只SQL语句交给StatementHandler
            return handler.<E>query(stmt);
        } catch (Exception e) {
            throw new SQLException(" SQL 执行异常" + e, e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public int update(MappedStatement ms, Object parameter) throws SQLException {
        Statement stmt = null;
        Connection connection = null;
        try {
            Configuration configuration = ms.getConfiguration();
            //新建一个StatementHandler
            StatementHandler handler = configuration.newStatementHandler(ms, parameter);
            //准备语句
            connection = dataSource.getConnection();
            stmt = handler.prepare(connection);
            // 设置参数
            handler.parameterize(stmt);
            //StatementHandler.update
            return handler.update(stmt);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
