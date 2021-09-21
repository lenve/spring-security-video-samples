package org.javaboy.getcurrentuser;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
@RestController
public class HelloController {
    @GetMapping("/hello")
    public void hello(HttpServletRequest req) {
        System.out.println("req.getClass() = " + req.getClass());
        //获取当前登录用户名
        System.out.println("req.getRemoteUser() = " + req.getRemoteUser());
        //判断用户是否具备某一个角色
        System.out.println("req.isUserInRole(\"admin\") = " + req.isUserInRole("admin"));
        //获取当前登录成功的用户对象
        Authentication auth = (Authentication) req.getUserPrincipal();
        //获取当前登录的用户名
        System.out.println("auth.getName() = " + auth.getName());
        //获取当前登录的用户角色
        System.out.println("auth.getAuthorities() = " + auth.getAuthorities());
    }

    @GetMapping("/hello2")
    public void hello2(Authentication authentication) {
        System.out.println("authentication = " + authentication);
    }

    @GetMapping("/hello3")
    public void hello3(Principal principal) {
        System.out.println("principal = " + principal);
    }
}
