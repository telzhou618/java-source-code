package com.example.springframework.webmvc.mapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhougaojun
 */
public interface HandlerMapping {

    HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception;
}
