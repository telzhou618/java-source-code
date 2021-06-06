package org.example.builder.xml;

import org.example.datasource.DataSourceFactory;
import org.example.datasource.DruidDataSourceFactory;
import org.example.io.Resources;
import org.example.mapping.Environment;
import org.example.parsing.XNode;
import org.example.parsing.XPathParser;
import org.example.session.Configuration;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author zhou1
 */
public class XMLConfigBuilder {

    private Configuration configuration;
    private XPathParser parser;

    public XMLConfigBuilder(InputStream inputStream) {
        this.configuration = new Configuration();
        this.parser = new XPathParser(inputStream);
    }

    public Configuration parse() {
        try {
            XNode root = parser.evalNode("/configuration");
            // 解析数据源
            environmentsElement(root.evalNode("environments"));
            // 解析 mapper 映射文件
            mapperElement(root.evalNode("mappers"));
            return configuration;
        } catch (Exception e) {
            throw new RuntimeException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
        }
    }

    //7.环境
//	<environments default="development">
//	  <environment id="development">
//	    <transactionManager type="JDBC">
//	      <property name="..." value="..."/>
//	    </transactionManager>
//	    <dataSource type="POOLED">
//	      <property name="driver" value="${driver}"/>
//	      <property name="url" value="${url}"/>
//	      <property name="username" value="${username}"/>
//	      <property name="password" value="${password}"/>
//	    </dataSource>
//	  </environment>
//	</environments>
    private void environmentsElement(XNode context) throws Exception {
        if (context != null) {
            for (XNode child : context.getChildren()) {
                //7.2数据源
                DataSourceFactory dsFactory = dataSourceElement(child.evalNode("dataSource"));
                DataSource dataSource = dsFactory.getDataSource();
                Environment environment = new Environment(dataSource);
                configuration.setEnvironment(environment);
            }
        }
    }

    //7.2数据源
//<dataSource type="POOLED">
//  <property name="driver" value="${driver}"/>
//  <property name="url" value="${url}"/>
//  <property name="username" value="${username}"/>
//  <property name="password" value="${password}"/>
//</dataSource>
    private DataSourceFactory dataSourceElement(XNode context) throws Exception {
        if (context != null) {
            Properties props = context.getChildrenAsProperties();
            // 数据源工厂写死用 Druid
            DruidDataSourceFactory factory = new DruidDataSourceFactory();
            factory.setProperties(props);
            return factory;
        }
        throw new RuntimeException("Environment declaration requires a DataSourceFactory.");
    }

    //	10.4自动扫描包下所有映射器
//	<mappers>
//	  <package name="org.mybatis.builder"/>
//	</mappers>
    private void mapperElement(XNode parent) throws Exception {
        if (parent != null) {
            for (XNode child : parent.getChildren()) {
                // 只使用一种mapper加载方式
                String resource = child.getStringAttribute("resource");
                if (resource != null) {
                    InputStream inputStream = Resources.getResourceAsStream(resource);
                    //映射器比较复杂，调用XMLMapperBuilder
                    //注意在for循环里每个mapper都重新new一个XMLMapperBuilder，来解析
                    XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream, configuration, resource);
                    mapperParser.parse();
                } else {
                    throw new RuntimeException("A mapper element may only specify a url, resource or class, but not more than one.");
                }
            }
        }
    }
}
