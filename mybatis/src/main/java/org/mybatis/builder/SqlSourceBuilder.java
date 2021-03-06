package org.mybatis.builder;

import org.mybatis.parsing.GenericTokenParser;
import org.mybatis.parsing.TokenHandler;

/**
 * @author telzhou
 */
public class SqlSourceBuilder {

    public String parse(String originalSql) {
        ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler();
        //替换#{}中间的部分,如何替换，逻辑在ParameterMappingTokenHandler
        GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
        //返回静态SQL源码
        return parser.parse(originalSql);
    }

    public static class ParameterMappingTokenHandler implements TokenHandler{
        @Override
        public String handleToken(String content) {
            return "?";
        }
    }
}
