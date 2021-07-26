package org.javaboy.formlogin;

/**
 * @author 江南一点雨
 * @微信公众号 江南一点雨
 * @网站 http://www.itboyhub.com
 * @国际站 http://www.javaboy.org
 * @微信 a_java_boy
 * @GitHub https://github.com/lenve
 * @Gitee https://gitee.com/lenve
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Spring Security 配置类
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //开启权限的配置
                .authorizeRequests()
                //拦截所有请求，所有请求都要认证之后才能访问
                .anyRequest().authenticated()
                //用 and 方法开启下一轮配置
                .and()
                .formLogin()
                //配置登录页面，默认的登录页面是 /login  GET
                .loginPage("/mylogin.html")
                //配置登录接口，默认的登录接口是 /login  POST
                .loginProcessingUrl("/doLogin")
                //配置登录成功的跳转，这个是客户端重定向，推荐这个
//                .defaultSuccessUrl("/index",true)
                //配置登录成功的跳转页面，但是是服务端跳转
//                .successForwardUrl("/index")
//                .successHandler(savedRequestAwareAuthenticationSuccessHandler())
                .successHandler((req, resp, auth) -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("status", 200);
                    map.put("msg", "登录成功");
                    resp.setContentType("application/json;charset=utf-8");
                    resp.getWriter().write(new ObjectMapper().writeValueAsString(map));
                })
                //配置登录失败的跳转页面，注意，这种跳转方式是一个客户端跳转（重定向）
//                .failureUrl("/mylogin.html")
                //配置登录失败的跳转页面，注意，这个是服务端跳转
//                .failureForwardUrl("/mylogin.html")
//                .failureHandler(simpleUrlAuthenticationFailureHandler())
                .failureHandler((req, resp, e) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    Map<String, Object> map = new HashMap<>();
                    map.put("status", 500);
                    map.put("msg", e.getMessage());
                    resp.getWriter().write(new ObjectMapper().writeValueAsString(map));
                })
                //配置登录页面用户名的 key，这个参数默认是 username
                .usernameParameter("uname")
                //配置登录页面密码的 key，这个参数默认是 password
                .passwordParameter("passwd")
                //登录相关的页面接口可以直接通行
                .permitAll()
                .and()
                //开启注销登录的配置
                .logout()
                //注销登录的 URL 地址，这是一个 GET 请求
//                .logoutUrl("/logout")
                //可以配置两个注销地址，第一个 logout 是 get 请求，第二个 logout2 是 post 请求
                .logoutRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher("/logout", "GET"),
                        new AntPathRequestMatcher("/logout2", "POST")))
                //注销登录时，使 HttpSession 失效，默认为 true
                .invalidateHttpSession(true)
                //清除认证信息，默认为 true
                .clearAuthentication(true)
                //注销成功后的跳转地址
//                .logoutSuccessUrl("/mylogin.html")
                //注销成功后，返回一段 JSON
//                .logoutSuccessHandler((req, resp, auth) -> {
//                    resp.setContentType("application/json;charset=utf-8");
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("status", 200);
//                    map.put("msg", "注销登录成功");
//                    resp.getWriter().write(new ObjectMapper().writeValueAsString(map));
//                })
                .defaultLogoutSuccessHandlerFor((req, resp, auth) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    Map<String, Object> map = new HashMap<>();
                    map.put("status", 200);
                    map.put("msg", "使用 GET /logout 注销登录成功");
                    resp.getWriter().write(new ObjectMapper().writeValueAsString(map));
                }, new AntPathRequestMatcher("/logout", "GET"))
                .defaultLogoutSuccessHandlerFor((req, resp, auth) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    Map<String, Object> map = new HashMap<>();
                    map.put("status", 200);
                    map.put("msg", "使用 POST /logout2 注销登录成功");
                    resp.getWriter().write(new ObjectMapper().writeValueAsString(map));
                }, new AntPathRequestMatcher("/logout2", "POST"))
                .and()
                //关闭 CSRF 防御策略
                .csrf().disable();
    }

    ExceptionMappingAuthenticationFailureHandler exceptionMappingAuthenticationFailureHandler() {
        ExceptionMappingAuthenticationFailureHandler handler = new ExceptionMappingAuthenticationFailureHandler();
        Map<String, String> map = new HashMap<>();
        map.put("", "/mylogin.html");
        map.put("", "/mylogin2.html");
        handler.setExceptionMappings(map);
        return handler;
    }

    SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler() {
        SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler();
        handler.setDefaultFailureUrl("/mylogin.html");
        handler.setUseForward(true);
        return handler;
    }

    SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl("/index");
        handler.setAlwaysUseDefaultTargetUrl(true);
        handler.setTargetUrlParameter("target");
        return handler;
    }

}
