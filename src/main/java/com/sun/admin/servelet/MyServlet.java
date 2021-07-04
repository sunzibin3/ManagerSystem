package com.sun.admin.servelet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1、继承HttpServlet类
 * 2、标记@WebServlet注解
 */
@WebServlet(urlPatterns = "/my")//urlPatterns标识请求地址
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("sdsadsa");
    }
}
