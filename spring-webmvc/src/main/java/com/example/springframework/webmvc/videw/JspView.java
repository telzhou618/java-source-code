package com.example.springframework.webmvc.videw;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author zhougaojun
 */
public class JspView implements View{
    @Override
    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {

        // TODO 渲染JSP页面
    }
}
