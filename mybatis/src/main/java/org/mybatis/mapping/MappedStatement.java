package org.mybatis.mapping;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.mybatis.session.Configuration;

/**
 * @author zhou1
 */
@Getter
@Setter
@Accessors(chain = true)
public class MappedStatement {

    private Configuration configuration;
    // 查询ID 如： org.example.mapper.BlogMapper.selectBlog
    private String id;
    //SQL源码
    private String sql;
    private SqlCommandType sqlCommandType;
    // 返回值类型
    private Class<?> resultTypeClass;
}
