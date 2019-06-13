package com.lt.personal_stadiumbookingsystem.web.servlet;

import com.lt.personal_stadiumbookingsystem.entity.Account;
import com.lt.personal_stadiumbookingsystem.entity.Order;
import com.lt.personal_stadiumbookingsystem.service.IOrderService;
import com.lt.personal_stadiumbookingsystem.service.impl.OrderServiceImpl;
import com.lt.personal_stadiumbookingsystem.util.GsonUtil;
import com.lt.personal_stadiumbookingsystem.util.PrintUtil;
import com.lt.personal_stadiumbookingsystem.web.base.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * @作者: LinTan
 * @日期: 2019/4/26 21:58
 * @版本: 1.0
 * @描述: //订单Servlet类
 * 1.0: Initial Commit
 */

@WebServlet(name = "OrderServlet", urlPatterns = "/order")
public class OrderServlet extends BaseServlet {

    public String book(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintUtil.printMethodName();
        Order order = new Order();
        BeanUtils.populate(order, request.getParameterMap());

        IOrderService service = new OrderServiceImpl();
        Order result = service.book(order);
        String json = GsonUtil.objectToJson(result);
        System.out.println("创建订单: " + json);
        returnDataToApp(json, request, response);

        return null;
    }//创建订单。状态: 待付款

    public String pay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintUtil.printMethodName();
        Order order = new Order();
        BeanUtils.populate(order, request.getParameterMap());

        IOrderService service = new OrderServiceImpl();
        boolean isSuccess = service.pay(order);
        System.out.println("付款指定单号的订单: " + isSuccess);
        returnDataToApp(isSuccess, request, response);

        return null;
    }//付款指定单号的订单。状态: 待执行

    public String cancel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintUtil.printMethodName();
        Order order = new Order();
        BeanUtils.populate(order, request.getParameterMap());

        IOrderService service = new OrderServiceImpl();
        boolean isSuccess = service.cancel(order);
        System.out.println("取消指定单号的订单: " + isSuccess);
        returnDataToApp(isSuccess, request, response);

        if (isSuccess) {
            // TODO: 2019/6/7
        } else {
            // TODO: 2019/6/7
        }
        return null;
    }//取消指定单号的订单。状态: 已取消

    public String show(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintUtil.printMethodName();
        Order order = new Order();
        BeanUtils.populate(order, request.getParameterMap());

        IOrderService service = new OrderServiceImpl();
        Order result = service.show(order);
        String json = GsonUtil.objectToJson(result);
        System.out.println("展示指定单号的订单信息: " + json);
        returnDataToApp(json, request, response);

        return null;
    }//展示指定单号的订单信息

    public String showlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintUtil.printMethodName();
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer limit = Integer.valueOf(request.getParameter("limit"));
        Account account = new Account();
        BeanUtils.populate(account, request.getParameterMap());

        IOrderService service = new OrderServiceImpl();
        List<Order> result = service.showList(account, page, limit);
        String json = GsonUtil.objectToJson(result);
        System.out.println("展示指定账户的订单信息: " + json);
        returnDataToApp(json, request, response);

        return null;
    }//展示指定账户的订单信息

    public String showall(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintUtil.printMethodName();
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer limit = Integer.valueOf(request.getParameter("limit"));

        IOrderService service = new OrderServiceImpl();
        HashMap<String, Object> result = service.showAll(page, limit);
        Object beanList = result.get("beanList");
        List<Order> list = GsonUtil.objectCastList(beanList, Order.class);
        int count = (int) result.get("count");
        String json = GsonUtil.objectToJson(result);
        System.out.println("展示所有账户的订单信息(限管理员), page: " + page + ", limit: " + limit + ": " + json);
        returnDataToApp(json, request, response);

        if (!"null".equals(json)) {
            returnDataToBrowser(0, "查询成功", count, list, request, response);
        } else {
            returnDataToBrowser(-1, "查询失败", 0, null, request, response);
        }
        return null;
    }//展示所有账户的订单信息(限管理员)
}
