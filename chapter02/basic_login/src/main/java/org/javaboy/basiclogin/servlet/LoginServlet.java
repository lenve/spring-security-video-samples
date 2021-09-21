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
@WebServlet(urlPatterns = "/doLogin")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try {
            req.login(username, password);
        } catch (ServletException e) {
            System.out.println("登录失败！");
            resp.sendRedirect("/login.jsp");
            return;
        }
        //登录成功
        //获取当前登录成功的用户对象
        Principal userPrincipal = req.getUserPrincipal();
        if (userPrincipal != null) {
            //登录成功
            //如果用户具备 admin 角色，就去 /admin/hello 接口，否则就去 /hello 接口
            if (req.isUserInRole("admin")) {
                resp.sendRedirect("/admin/hello");
            } else {
                resp.sendRedirect("/hello");
            }
        } else {
            //登录失败
            resp.sendRedirect("/login.jsp");
        }
    }
}
