package com.lt.personal_stadiumbookingsystem.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @作者: LinTan
 * @日期: 2019/4/21 22:53
 * @版本: 1.0
 * @描述: //编码过滤器
 * 1.0: Initial Commit
 */

@WebFilter(filterName = "Fliter2_Platform", urlPatterns = "/*")
public class Fliter2_Platform implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String header = request.getHeader("User-Agent");
        String addr = request.getRemoteAddr();
        String url = String.valueOf(request.getRequestURL());

        if (!url.contains("/asset/")) {
            if (header.startsWith("Mozilla")) {
                System.out.print("From Browser, IP: " + addr + ", ");
                request.setAttribute("account_role", "admin");
            } else if (header.startsWith("okhttp")) {
                System.out.print("From Android App, IP: " + addr + ", ");
                request.setAttribute("account_role", "user");
            } else {
                System.out.print("From " + header + ", IP: " + addr + ", ");
            }
            System.out.print("URL: " + url);
            System.out.println();
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
