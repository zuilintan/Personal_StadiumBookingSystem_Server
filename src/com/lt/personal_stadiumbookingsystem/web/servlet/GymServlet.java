package com.lt.personal_stadiumbookingsystem.web.servlet;

import com.lt.personal_stadiumbookingsystem.entity.Gym;
import com.lt.personal_stadiumbookingsystem.service.IGymService;
import com.lt.personal_stadiumbookingsystem.service.impl.GymServiceImpl;
import com.lt.personal_stadiumbookingsystem.util.GsonUtil;
import com.lt.personal_stadiumbookingsystem.util.PrintUtil;
import com.lt.personal_stadiumbookingsystem.web.base.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @作者: LinTan
 * @日期: 2019/5/3 20:19
 * @版本: 1.0
 * @描述: //场馆Servlet类
 * 1.0: Initial Commit
 */

@WebServlet(name = "GymServlet", urlPatterns = "/gym")
public class GymServlet extends BaseServlet {

    public String vindicate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintUtil.printMethodName();
        Gym gym = new Gym();
        BeanUtils.populate(gym, request.getParameterMap());

        IGymService service = new GymServiceImpl();
        boolean isSuccess = service.vindicate(gym);
        System.out.println("维护场馆: " + isSuccess);
        returnDataToApp(isSuccess, request, response);

        if (isSuccess) {
            // TODO: 2019/6/7  
        } else {
            // TODO: 2019/6/7  
        }
        return null;
    }//维护场馆(限管理员)

    public String operate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintUtil.printMethodName();
        Gym gym = new Gym();
        BeanUtils.populate(gym, request.getParameterMap());

        IGymService service = new GymServiceImpl();
        boolean isSuccess = service.operate(gym);
        System.out.println("营业场馆: " + isSuccess);
        returnDataToApp(isSuccess, request, response);

        if (isSuccess) {
            // TODO: 2019/6/7
        } else {
            // TODO: 2019/6/7
        }
        return null;
    }//营业场馆(限管理员)

    public String showall(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintUtil.printMethodName();

        IGymService service = new GymServiceImpl();
        List<Gym> result = service.showAll();
        String json = GsonUtil.objectToJson(result);
        System.out.println("展示所有场馆的信息: " + json);
        returnDataToApp(json, request, response);

        if (!"null".equals(json)) {
            returnDataToBrowser(0, "查询成功", result.size(), result, request, response);
        } else {
            returnDataToBrowser(-1, "查询失败", 0, null, request, response);
        }
        return null;
    }//展示所有场馆的信息
}
