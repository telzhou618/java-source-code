package org.mybatis.mapping;

import org.mybatis.session.Configuration;

/**
 * @author telzhou
 */
public class MappedStatement {

    private Configuration configuration;
    // 查询ID 如： org.example.mapper.BlogMapper.selectBlog
    private String id;
    //SQL源码
    private String sql;
    private SqlCommandType sqlCommandType;
    // 返回值类型
    private Class<?> resultTypeClass;

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(SqlCommandType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    public Class<?> getResultTypeClass() {
        return resultTypeClass;
    }

    public void setResultTypeClass(Class<?> resultTypeClass) {
        this.resultTypeClass = resultTypeClass;
    }
}
