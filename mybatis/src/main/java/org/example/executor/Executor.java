package org.example.executor;

import org.example.mapping.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zhou1
 */
public interface Executor {

    //查询，带分页
    <E> List<E> query(MappedStatement ms, Object parameter) throws SQLException;

    int update(MappedStatement ms, Object parameter) throws SQLException;


}
