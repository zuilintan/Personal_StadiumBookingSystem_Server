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
    <title>登录</title>
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" media="all" href="<%=contextPath%>/asset/layui/css/layui.css">
    <link rel="stylesheet" media="all" href="<%=contextPath%>/asset/login.css">
</head>
<body onload="disableBack()">
<div class="container">
    <div id="title">
        <h2>体育场馆预订系统</h2>
        <p id="msg">${msg}</p>
    </div>
    <div>
        <form class="layui-form" name="formAccountLogin" method="post"
              action="<%=contextPath%>/account?action=login">
            <div class="layui-form-item">
                <label class="layui-form-label">账号</label>
                <div class="layui-input-inline">
                    <input class="layui-input" type="text" name="account_name"
                           placeholder="请输入账号" lay-verify="required" autocomplete="on">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">密码</label>
                <div class="layui-input-inline">
                    <input class="layui-input" type="password" name="account_passwd"
                           placeholder="请输入密码" lay-verify="required" autocomplete="on">
                </div>
            </div>
            <div class="layui-form-item">
                <button class="layui-btn" type="button" lay-filter="filterAccountLogin" lay-submit>登陆</button>
            </div>
            <p>Copyright © 2019 LT. All rights reserved</p>
        </form>
    </div>
</div>
<script src="<%=contextPath%>/asset/layui/layui.js"></script>
<script>
    function disableBack() {
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', function () {
            history.pushState(null, null, document.URL);
        });
    }//清除后退动作

    layui.use('form', function () {
        var form = layui.form;
        form.on('submit(filterAccountLogin)', function () {
            document.formAccountLogin.submit();
            return false;
        });
    });//提交表单
</script>
</body>
</html>
