package com.lt.personal_stadiumbookingsystem.web.filter;

import com.lt.personal_stadiumbookingsystem.entity.Account;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @作者: LinTan
 * @日期: 2019/4/21 22:53
 * @版本: 1.0
 * @描述: //编码过滤器
 * 1.0: Initial Commit
 */

@WebFilter(filterName = "Fliter3_Login", urlPatterns = "/page/private/*")
public class Fliter3_Login implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String contextPath = request.getContextPath();
        Account account = (Account) request.getSession().getAttribute("admin");

        if (account == null || account.getAccount_name().isEmpty()) {
            PrintWriter printWriter = response.getWriter();
            printWriter.println("<html>");
            printWriter.println("<script>");
            printWriter.println("window.open('" + contextPath + "/page/tourist.jsp','_top')");
            printWriter.println("</script>");
            printWriter.println("</html>");
            //response.sendRedirect(contextPath + "/page/tourist.jsp");//弃用，Iframe无法重定向外层布局
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
