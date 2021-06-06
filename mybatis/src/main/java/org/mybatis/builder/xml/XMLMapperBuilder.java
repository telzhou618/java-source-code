package org.mybatis.builder.xml;

import org.mybatis.builder.SqlSourceBuilder;
import org.mybatis.io.Resources;
import org.mybatis.mapping.MappedStatement;
import org.mybatis.mapping.SqlCommandType;
import org.mybatis.parsing.XNode;
import org.mybatis.parsing.XPathParser;
import org.mybatis.session.Configuration;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

/**
 * @author zhou1
 */
public class XMLMapperBuilder {

    private Configuration configuration;
    private XPathParser parser;
    private String resource;


    public XMLMapperBuilder(InputStream inputStream, Configuration configuration, String resource) {
        this.parser = new XPathParser(inputStream);
        this.configuration = configuration;
        this.resource = resource;
    }

    public void parse() {
        configurationElement(parser.evalNode("/mapper"));
    }

    /**
     * 解析 mapper 节点，解析结果保存到 mappedStatements
     *
     * <mapper namespace="org.mybatis.example.BlogMapper">
     * <select id="selectBlog" parameterType="int" resultType="Blog">
     * select * from Blog where id = #{id}
     * </select>
     * </mapper>
     *
     * @param context
     */
    private void configurationElement(XNode context) {
        try {
            //1.配置namespace
            String namespace = context.getStringAttribute("namespace");
            if (namespace.equals("")) {
                throw new RuntimeException("Mapper's namespace cannot be empty");
            }
            // 解析 mapper class 中的方法，
            Class<?> mapperClass = Resources.classForName(namespace);
            configuration.addMapper(mapperClass);
            // 解析 mapper xml,
            List<XNode> xNodes = context.evalNodes("select|insert|update|delete");
            for (XNode xNode : xNodes) {
                // 获取命令类型(select|insert|update|delete)
                String nodeName = xNode.getName();
                String id = xNode.getStringAttribute("id");
                String resultType = xNode.getStringAttribute("resultType");
                String originalSql = xNode.getStringBody();
                SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));
                Class<?> aClass = Resources.classForName(resultType);
                MappedStatement ms = new MappedStatement()
                        .setConfiguration(configuration)
                        .setId(namespace + "." + id)
                        .setSqlCommandType(sqlCommandType)
                        .setResultTypeClass(aClass);
                // 解析SQL
                SqlSourceBuilder sqlSourceBuilder = new SqlSourceBuilder();
                String sql = sqlSourceBuilder.parse(originalSql);
                ms.setSql(sql);
                configuration.addMappedStatement(ms);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error parsing Mapper XML. Cause: " + e, e);
        }
    }
}

