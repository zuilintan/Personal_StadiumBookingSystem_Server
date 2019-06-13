package com.lt.personal_stadiumbookingsystem.web.servlet;

import com.lt.personal_stadiumbookingsystem.entity.Account;
import com.lt.personal_stadiumbookingsystem.service.IAccountService;
import com.lt.personal_stadiumbookingsystem.service.impl.AccountServiceImpl;
import com.lt.personal_stadiumbookingsystem.util.GsonUtil;
import com.lt.personal_stadiumbookingsystem.util.PrintUtil;
import com.lt.personal_stadiumbookingsystem.web.base.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @作者: LinTan
 * @日期: 2019/4/21 22:54
 * @版本: 1.0
 * @描述: //用户Servlet类
 * 1.0: Initial Commit
 *
 * <p>
 * 注意: getWrite().print()会将输出结果转为字符串
 */

@WebServlet(name = "AccountServlet", urlPatterns = "/account")
public class AccountServlet extends BaseServlet {

    public String register(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintUtil.printMethodName();
        Account account = new Account();
        BeanUtils.populate(account, request.getParameterMap());

        IAccountService service = new AccountServiceImpl();
        boolean isSuccess = service.register(account);
        System.out.println("注册账户: " + isSuccess);
        returnDataToApp(isSuccess, request, response);

        return null;
    }//注册账户

    public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintUtil.printMethodName();
        Account account = new Account();
        System.out.println(request.getParameter("account_name"));
        BeanUtils.populate(account, request.getParameterMap());
        account.setAccount_role((String) request.getAttribute("account_role"));

        IAccountService service = new AccountServiceImpl();
        boolean isSuccess = service.login(account);
        System.out.println("登录账户: " + isSuccess);
        returnDataToApp(isSuccess, request, response);

        String path;
        if (isSuccess) {
            account = service.show(account);
            request.getSession().setAttribute("admin", account);
            path = "/page/private/console.jsp";
        } else {
            request.getSession().setAttribute("msg", "用户名或密码有误");
            path = "/page/login.jsp";
        }
        return path;
    }//登录账户

    public String append(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintUtil.printMethodName();
        Float offset = Float.valueOf(request.getParameter("amount"));
        System.out.println(request.getParameter("account_name"));
        Account account = new Account();
        BeanUtils.populate(account, request.getParameterMap());

        IAccountService service = new AccountServiceImpl();
        boolean isSuccess = service.append(offset, account);
        System.out.println("充值账户: " + isSuccess);
        returnDataToApp(isSuccess, request, response);

        if (isSuccess) {
            // TODO: 2019/6/7  处理LayUI返回的数据并响应
        } else {
            // TODO: 2019/6/7  处理LayUI返回的数据并响应
        }
        return null;
    }//充值账户

    public String edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintUtil.printMethodName();
        Account account = new Account();
        Map<String, String[]> parameterMap = request.getParameterMap();
        BeanUtils.populate(account, parameterMap);

        IAccountService service = new AccountServiceImpl();
        boolean isSuccess = service.edit(parameterMap, account);
        System.out.println("编辑指定账户的信息: " + isSuccess);
        returnDataToApp(isSuccess, request, response);

        String path;
        if (isSuccess) {
            request.getSession().setAttribute("msg", "修改成功");
            String jspName = (String) request.getSession().getAttribute("jspName");
            path = "/page/private/" + jspName;
        } else {
            request.getSession().setAttribute("msg", "修改失败");
            String jspName = (String) request.getSession().getAttribute("jsp_name");
            path = "/page/private/" + jspName;
        }
        return path;
    }//编辑账户信息

    public String show(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintUtil.printMethodName();
        Account account = new Account();
        BeanUtils.populate(account, request.getParameterMap());

        IAccountService service = new AccountServiceImpl();
        Account result = service.show(account);
        String json = GsonUtil.objectToJson(result);
        System.out.println("展示指定账户的信息: " + json);
        returnDataToApp(json, request, response);

        return null;
    }//展示指定账户的信息

    public String showall(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintUtil.printMethodName();
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer limit = Integer.valueOf(request.getParameter("limit"));

        IAccountService service = new AccountServiceImpl();
        HashMap<String, Object> result = service.showAll(page, limit);
        Object beanList = result.get("beanList");
        List<Account> list = GsonUtil.objectCastList(beanList, Account.class);
        int count = (int) result.get("count");
        String json = GsonUtil.objectToJson(list);
        System.out.println("展示所有账户的信息(限管理员), page: " + page + ", limit: " + limit + ": " + json);
        returnDataToApp(json, request, response);

        if (!"null".equals(json)) {
            returnDataToBrowser(0, "查询成功", count, list, request, response);
        } else {
            returnDataToBrowser(-1, "查询失败", 0, null, request, response);
        }
        return null;
    }//展示所有账户的信息(限管理员)
}
