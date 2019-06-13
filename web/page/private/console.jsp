<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
    String uri = request.getRequestURI();
    String jspName = uri.substring(uri.lastIndexOf("/") + 1);
    request.getSession().setAttribute("jspName", jspName);
    request.getSession().setAttribute("dataFormat", null);
%>
<html>
<head>
    <title>Index</title>
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" media="all" href="<%=contextPath%>/asset/layui/css/layui.css">
    <link rel="stylesheet" media="all" href="<%=contextPath%>/asset/common.css">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">体育场馆预订系统</div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a>${admin.account_name}</a>
                <dl class="layui-nav-child">
                    <dd><a href="<%=contextPath%>/page/private/main_editinfo.jsp" target="_iframe">修改密码</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item">
                <a href="<%=contextPath%>/page/logout.jsp ">退出登录</a>
            </li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree">
                <li class="layui-nav-item layui-nav-itemed">
                    <a>功能管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="<%=contextPath%>/page/private/main_account.jsp" target="_iframe">用户管理</a></dd>
                        <dd><a href="<%=contextPath%>/page/private/main_gym.jsp" target="_iframe">场馆管理</a></dd>
                        <dd><a href="<%=contextPath%>/page/private/main_order.jsp" target="_iframe">订单管理</a></dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>

    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <iframe name="_iframe" frameborder="0" src="<%=contextPath%>/page/private/main_welcome.jsp"></iframe>
            </div>
        </div>
    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->
        © 控制台
    </div>
</div>
<script src="<%=contextPath%>/asset/layui/layui.js"></script>
<script>
    //左侧代码区域
    layui.use('element', function () {
        var element = layui.element;
    });
</script>
</body>
</html>
