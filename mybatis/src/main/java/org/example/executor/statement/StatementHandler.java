package org.example.executor.statement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author zhou1
 */
public interface StatementHandler {

    /**
     * 准备语句
     * @param connection
     * @return
     * @throws SQLException
     */
    Statement prepare(Connection connection) throws SQLException;

    /**
     * 设置参数
     * @param statement
     * @throws SQLException
     */
    void parameterize(Statement statement) throws SQLException;

    /**
     * 执行语句
     * @param statement
     * @param <E>
     * @return
     * @throws SQLException
     */
    <E> List<E> query(Statement statement) throws SQLException;

    int update(Statement statement) throws  SQLException;
}
