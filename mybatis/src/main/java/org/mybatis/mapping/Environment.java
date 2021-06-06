package org.mybatis.mapping;

import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;

/**
 * @author zhou1
 */
@Getter
@Setter
public class Environment {

    private final DataSource dataSource;

    public Environment(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
