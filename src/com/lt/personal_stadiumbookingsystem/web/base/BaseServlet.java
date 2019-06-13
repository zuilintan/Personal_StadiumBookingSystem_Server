package com.lt.personal_stadiumbookingsystem.web.base;

import com.lt.personal_stadiumbookingsystem.util.GsonUtil;
import com.lt.personal_stadiumbookingsystem.util.PrintUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @作者: LinTan
 * @日期: 2019/4/21 22:53
 * @版本: 1.0
 * @描述: //Servlet基类
 * 1.0: Initial Commit
 */

@WebServlet(name = "BaseServlet")
public class BaseServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        String actionName = request.getParameter("action");//获取客户端提交到服务端的键名为action的值。eg: http://localhost:8080/Stadium/ServletDemo01?action=addStu
        if (actionName == null || "".equals(actionName) || "".equals(actionName.trim())) {
            actionName = "index";
        }
        try {
            Class<? extends BaseServlet> clazz = this.getClass();
            Method actionMethod = clazz.getMethod(actionName, HttpServletRequest.class, HttpServletResponse.class);//获取clazz类中方法名为actionName的方法
            if (actionMethod != null) {
                String resultPath = (String) actionMethod.invoke(this, request, response);//调用找到的方法，并拿到其返回值
                if (resultPath != null) {
                    returnPathToBrowser(resultPath, request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> void returnDataToApp(T result, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accountRole = (String) request.getAttribute("account_role");
        if ("user".equals(accountRole)) {
            PrintWriter printWriter = response.getWriter();
            switch (String.valueOf(result)) {
                case "false":
                    printWriter.print("failure");
                    break;

                case "true":
                    printWriter.print("success");
                    break;

                case "null":
                    printWriter.print("failure");
                    break;

                default:
                    printWriter.print(result);
                    break;
            }
        }
    }

    public <T> void returnPathToBrowser(T result, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accountRole = (String) request.getAttribute("account_role");
        if ("admin".equals(accountRole)) {
            String dataFormat = (String) request.getAttribute("dataFormat");
            if (dataFormat == null) {
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + result);//服务端的重定向
            }
        }
    }

    public <T> void returnDataToBrowser(T data, HttpServletRequest request, HttpServletResponse response) throws IOException {
        returnDataToBrowser(0, "", 0, data, request, response);
    }

    public <T> void returnDataToBrowser(int code, String msg, int count, T data, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accountRole = (String) request.getAttribute("account_role");
        if ("admin".equals(accountRole)) {
            String dataFormat = (String) request.getSession().getAttribute("dataFormat");
            if (dataFormat != null) {
                PrintWriter printWriter = response.getWriter();
                switch (dataFormat) {
                    case "layuiDataTable":
                        Map<String, Object> map = new LinkedHashMap<>();
                        map.put("code", code);
                        map.put("msg", msg);
                        map.put("count", count);
                        map.put("data", data);
                        String json = GsonUtil.objectToJson(map);
                        printWriter.print(json);
                        break;

                    default:
                        break;
                }
            }
        }
    }

    public String index(HttpServletRequest request, HttpServletResponse response) {
        PrintUtil.printMethodName();
        return "/index.jsp";
    }
}
