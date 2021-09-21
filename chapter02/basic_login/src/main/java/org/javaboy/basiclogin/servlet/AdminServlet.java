package org.javaboy.basiclogin.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * @author 江南一点雨
 * @微信公众号 江南一点雨
 * @网站 http://www.itboyhub.com
 * @国际站 http://www.javaboy.org
 * @微信 a_java_boy
 * @GitHub https://github.com/lenve
 * @Gitee https://gitee.com/lenve
 */
@WebServlet(urlPatterns = "/admin/hello")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Principal userPrincipal = req.getUserPrincipal();
        if (userPrincipal == null) {
            //说明没登录
            resp.setStatus(401);
            resp.getWriter().write("please login!");
        }else if(!req.isUserInRole("admin")){
            resp.setStatus(403);
            resp.getWriter().write("forbidden");
        }else {
            resp.getWriter().write("hello admin!");
        }
    }
}
