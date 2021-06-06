package com.example.springframework.webmvc.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhougaojun
 */
public abstract class FrameworkServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.doService(req, resp);
        } catch (ServletException | IOException ex) {
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException("Request processing failed", e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.doService(req, resp);
        } catch (ServletException | IOException ex) {
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException("Request processing failed", e);
        }
    }

    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        this.doDispatch(request, response);
    }

    protected abstract void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception;

}
