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
    <title>账户列表</title>
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" media="all" href="<%=contextPath%>/asset/layui/css/layui.css">
    <link rel="stylesheet" media="all" href="<%=contextPath%>/asset/common.css">
</head>
<body>
<div>
    <div id="title">
        <h2>信息修改</h2>
        <p id="msg">${msg}</p>
    </div>
    <div>
        <form class="layui-form" name="formAccountEdit" method="post"
              action="<%=contextPath%>/account?action=edit">
            <div class="layui-form-item">
                <label class="layui-form-label">账号</label>
                <div class="layui-input-inline">
                    <input class="layui-input" type="text" name="account_name"
                           autocomplete="off" value="${admin.account_name}" readonly>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">密码</label>
                <div class="layui-input-inline">
                    <input class="layui-input" type="text" name="account_passwd"
                           autocomplete="off">
                </div>
            </div>
            <div class="layui-form-item">
                <button class="layui-btn" id="editInfoBtn" type="button" lay-filter="filterAccountEdit" lay-submit>
                    修改
                </button>
            </div>
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
        form.on('submit(filterAccountEdit)', function () {
            document.formAccountEdit.submit();
            return false;
        });
    });//提交表单
</script>
</body>
</html>
