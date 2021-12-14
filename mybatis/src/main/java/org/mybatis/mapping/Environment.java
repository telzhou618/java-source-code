package org.mybatis.mapping;

import javax.sql.DataSource;

/**
 * @author telzhou
 */
public class Environment {

    private final DataSource dataSource;

    public Environment(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

}
