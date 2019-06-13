package com.lt.personal_stadiumbookingsystem.web.servlet;

import com.lt.personal_stadiumbookingsystem.web.base.BaseServlet;
import com.lt.personal_stadiumbookingsystem.entity.Gym;
import com.lt.personal_stadiumbookingsystem.entity.Site;
import com.lt.personal_stadiumbookingsystem.service.ISiteService;
import com.lt.personal_stadiumbookingsystem.service.impl.SiteServiceImpl;
import com.lt.personal_stadiumbookingsystem.util.GsonUtil;
import com.lt.personal_stadiumbookingsystem.util.PrintUtil;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @作者: LinTan
 * @日期: 2019/4/26 18:11
 * @版本: 1.0
 * @描述: //场地Servlet类
 * 1.0: Initial Commit
 */

@WebServlet(name = "SiteServlet", urlPatterns = "/site")
public class SiteServlet extends BaseServlet {

    public String showlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintUtil.printMethodName();
        Gym gym = new Gym();
        BeanUtils.populate(gym, request.getParameterMap());

        ISiteService service = new SiteServiceImpl();
        List<Site> result = service.showList(gym);
        String json = GsonUtil.objectToJson(result);
        System.out.println("展示指定场馆的场地信息: " + json);
        returnDataToApp(json, request, response);

        return null;
    }//展示指定场馆的场地信息
}
