package org.javaboy.custom_login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 江南一点雨
 * @微信公众号 江南一点雨
 * @网站 http://www.itboyhub.com
 * @国际站 http://www.javaboy.org
 * @微信 a_java_boy
 * @GitHub https://github.com/lenve
 * @Gitee https://gitee.com/lenve
 */
@RestController
public class LoginController {
    @PostMapping("/login")
    public String login(String username, String password, HttpServletRequest req) {
        try {
            //去登录
            req.login(username, password);
            return "success";
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return "fail";
    }

    @Autowired
    AuthenticationManager authenticationManager;


    @PostMapping("/login2")
    public String login2(String username, String password, HttpServletRequest req) {
        try {
            //去登录
            //认证成功后，会返回一个认证后的 Authentication
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fail";
    }


}
