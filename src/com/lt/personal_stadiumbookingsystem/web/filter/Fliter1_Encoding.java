package com.lt.personal_stadiumbookingsystem.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
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

@WebFilter(filterName = "Fliter1_Encoding", urlPatterns = "/*",
           initParams = {
                   @WebInitParam(name = "encoding", value = "UTF-8"),
           })
public class Fliter1_Encoding implements Filter {
    private String mEncoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        mEncoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String method = request.getMethod();
        String url = String.valueOf(request.getRequestURL());

        if (!url.contains("/asset/")) {
            if (method.equalsIgnoreCase("Get")) {
                response.setContentType("text/html;charset=utf-8");//指定服务端向客户端响应的编码
                System.out.print("Request: " + "Get" + ", ");
            } else if (method.equalsIgnoreCase("Post")) {
                request.setCharacterEncoding(mEncoding);//指定客户端向服务端请求的编码(eg: 数据库取值)
                response.setContentType("text/html;charset=utf-8");//指定服务端向客户端响应的编码
                System.out.print("Request: " + "Post" + ", ");
            } else {
                System.out.print("Request: " + method + ", ");
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        mEncoding = null;
    }
}
