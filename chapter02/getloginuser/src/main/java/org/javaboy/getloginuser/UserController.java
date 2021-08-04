package org.javaboy.getloginuser;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CompletableFuture;

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
public class UserController {

    @GetMapping("/hello")
    public void hello(HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        out.write("hello");
        out.flush();
    }

    @GetMapping("/userinfo")
    public void userInfo() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        System.out.println("authentication.getName() = " + authentication.getName());
        System.out.println("authentication.getAuthorities() = " + authentication.getAuthorities());
        new Thread(()->{
            Authentication a1 = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("a1.getAuthorities() = " + a1.getAuthorities());
            System.out.println("a1.getName() = " + a1.getName());
        }).start();
    }

    @GetMapping("/userinfo2")
    public void userinfo2(HttpServletRequest req, HttpServletResponse resp) {
        AsyncContext asyncContext = req.startAsync();
        CompletableFuture.runAsync(() -> {
            //给客户端响应
            try {
                //当在这里给客户端响应的时候，并不会去 SecurityContextHolder 中获取用户信息保存到 HttpSession
                PrintWriter out = resp.getWriter();
                out.write("hello");
                out.flush();
                asyncContext.complete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
