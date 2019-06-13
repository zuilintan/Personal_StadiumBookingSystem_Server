<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
    String uri = request.getRequestURI();
    String jspName = uri.substring(uri.lastIndexOf("/") + 1);
    request.getSession().setAttribute("jspName", jspName);
    request.getSession().setAttribute("dataFormat", "layuiDataTable");
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
<body onload="disablePageBack()">

<table class="layui-hide" id="accountTable" lay-filter="accountFilter"></table>

<script src="<%=contextPath%>/asset/layui/layui.js"></script>
<script type="text/html" id="accountBar">
    <a class="layui-btn layui-btn-xs" lay-event="append">充值60硬币</a>
</script>
<script>
    function disablePageBack() {
        //消除后退动作
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', function () {
            history.pushState(null, null, document.URL);
        });
    }
</script>
<script>
    layui.use('table', function () {
        var table = layui.table;

        //初始化数据表格
        var tableRender = table.render({
            elem: '#accountTable',
            height: 'full-0',
            url: '<%=contextPath%>/account?action=showall',
            cellMinWidth: 80,
            cols: [[
                {field: 'account_id', title: 'ID', fixed: 'left', sort: true},
                {field: 'account_name', title: '账号'},
                {field: 'account_nickname', title: '昵称'},
                {field: 'account_realname', title: '实名'},
                {field: 'account_coin', title: '硬币', sort: true},
                {field: 'account_sex', title: '性别', sort: true},
                {field: 'account_phone', title: '电话'},
                {field: 'account_email', title: '邮箱'},
                {field: 'account_qq', title: 'QQ'},
                {title: '操作', fixed: 'right', toolbar: '#accountBar'}
            ]],
            page: true
        });

        //监听行工具事件
        table.on('tool(accountFilter)', function (obj) {
            var data = obj.data;
            var $ = layui.$;
            if (obj.event === 'append') {
                layer.confirm('确认充值吗？', function (index) {
                    $.get("<%=contextPath%>/account?action=append&account_name=" + data.account_name + "&amount=60", function () {
                        tableRender.reload();
                    });
                    layer.msg('操作完成');
                    layer.close(index);
                });
            }
        });
    });
</script>
</body>
</html>
