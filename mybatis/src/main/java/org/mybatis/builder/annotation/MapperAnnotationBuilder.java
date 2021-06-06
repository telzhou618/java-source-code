package org.mybatis.builder.annotation;

import org.mybatis.annotations.*;
import org.mybatis.builder.SqlSourceBuilder;
import org.mybatis.mapping.MappedStatement;
import org.mybatis.mapping.SqlCommandType;
import org.mybatis.session.Configuration;

import java.lang.reflect.*;
import java.util.Collection;

/**
 * @author zhou1
 */
public class MapperAnnotationBuilder {

    private Configuration configuration;
    private Class<?> type;

    public MapperAnnotationBuilder(Configuration configuration, Class<?> type) {
        this.configuration = configuration;
        this.type = type;
    }

    public void parse() {
        Method[] methods = type.getMethods();
        for (Method method : methods) {
            try {
                // issue #237
                if (!method.isBridge()) {
                    //TODO 这里还可以优化
                    MappedStatement mappedStatement = new MappedStatement();
                    mappedStatement.setId(type.getName() + "." + method.getName());
                    mappedStatement.setConfiguration(configuration);
                    mappedStatement.setResultTypeClass(getReturnType(method));
                    if (method.isAnnotationPresent(Select.class)) {
                        mappedStatement.setSql(getSql(method.getAnnotation(Select.class).value()));
                        mappedStatement.setSqlCommandType(SqlCommandType.SELECT);
                        configuration.addMappedStatement(mappedStatement);
                    } else if (method.isAnnotationPresent(Insert.class)) {
                        mappedStatement.setSql(getSql(method.getAnnotation(Insert.class).value()));
                        mappedStatement.setSqlCommandType(SqlCommandType.INSERT);
                        configuration.addMappedStatement(mappedStatement);
                    } else if (method.isAnnotationPresent(Update.class)) {
                        mappedStatement.setSql(getSql(method.getAnnotation(Update.class).value()));
                        mappedStatement.setSqlCommandType(SqlCommandType.UPDATE);
                        configuration.addMappedStatement(mappedStatement);
                    } else if (method.isAnnotationPresent(Delete.class)) {
                        mappedStatement.setSql(getSql(method.getAnnotation(Delete.class).value()));
                        mappedStatement.setSqlCommandType(SqlCommandType.DELETE);
                        configuration.addMappedStatement(mappedStatement);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 替换 #{*} 为 ?
     */
    private String getSql(String originalSql) {
        SqlSourceBuilder sqlSourceBuilder = new SqlSourceBuilder();
        return sqlSourceBuilder.parse(originalSql);
    }

    /**
     * 解析接口方法的返回值类型,从而获得SQL执行语句的返回值类型
     * User  -> User.class
     * List<User>  -> User.class
     */
    private Class<?> getReturnType(Method method) {
        Class<?> returnType = method.getReturnType();
        // issue #508
        if (void.class.equals(returnType)) {
            ResultType rt = method.getAnnotation(ResultType.class);
            if (rt != null) {
                returnType = rt.value();
            }
        } else if (Collection.class.isAssignableFrom(returnType)) {
            Type returnTypeParameter = method.getGenericReturnType();
            if (returnTypeParameter instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) returnTypeParameter).getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length == 1) {
                    returnTypeParameter = actualTypeArguments[0];
                    if (returnTypeParameter instanceof Class) {
                        returnType = (Class<?>) returnTypeParameter;
                    } else if (returnTypeParameter instanceof ParameterizedType) {
                        // (issue #443) actual type can be a also a parameterized type
                        returnType = (Class<?>) ((ParameterizedType) returnTypeParameter).getRawType();
                    } else if (returnTypeParameter instanceof GenericArrayType) {
                        Class<?> componentType = (Class<?>) ((GenericArrayType) returnTypeParameter).getGenericComponentType();
                        // (issue #525) support List<byte[]>
                        returnType = Array.newInstance(componentType, 0).getClass();
                    }
                }
            }
        }
        return returnType;
    }
}
