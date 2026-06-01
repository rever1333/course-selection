package com.xuan.selectcourse.security;

import org.springframework.ui.Model;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
//自定义拦截器到security过滤链中
@WebFilter(urlPatterns = {"/user/login","/admin_login"})
public class BeforeLoginFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        if (username != null) {
            session.setAttribute("username", username);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
