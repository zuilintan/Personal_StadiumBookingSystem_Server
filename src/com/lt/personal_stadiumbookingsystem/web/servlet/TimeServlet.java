package com.lt.personal_stadiumbookingsystem.web.servlet;

import com.lt.personal_stadiumbookingsystem.web.base.BaseServlet;
import com.lt.personal_stadiumbookingsystem.entity.Site;
import com.lt.personal_stadiumbookingsystem.service.ITimeService;
import com.lt.personal_stadiumbookingsystem.service.impl.TimeServiceImpl;
import com.lt.personal_stadiumbookingsystem.util.GsonUtil;
import com.lt.personal_stadiumbookingsystem.util.PrintUtil;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @作者: LinTan
 * @日期: 2019/4/26 18:11
 * @版本: 1.0
 * @描述: //时段Servlet类
 * 1.0: Initial Commit
 */

@WebServlet(name = "TimeServlet", urlPatterns = "/time")
public class TimeServlet extends BaseServlet {

    public String showlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintUtil.printMethodName();
        Site site = new Site();
        BeanUtils.populate(site, request.getParameterMap());

        ITimeService service = new TimeServiceImpl();
        List<Map<String, Object>> result = service.showList(site);
        String json = GsonUtil.objectToJson(result);
        System.out.println("展示特定条件的时段: " + json);
        returnDataToApp(json, request, response);

        return null;
    }//展示特定条件的时段
}
