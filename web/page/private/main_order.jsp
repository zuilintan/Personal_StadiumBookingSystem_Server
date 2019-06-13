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
    <title>订单列表</title>
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" media="all" href="<%=contextPath%>/asset/layui/css/layui.css">
    <link rel="stylesheet" media="all" href="<%=contextPath%>/asset/common.css">
</head>
<body onload="disablePageBack()">

<table class="layui-hide" id="orderTable" lay-filter="orderFilter"></table>

<script src="<%=contextPath%>/asset/layui/layui.js"></script>
<script type="text/html" id="orderBar">
    <a class="layui-btn layui-btn-xs" lay-event="cancel">取消订单</a>
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
            elem: '#orderTable',
            height: 'full-0',
            url: '<%=contextPath%>/order?action=showall',
            cellMinWidth: 80,
            cols: [[
                {field: 'order_id', title: 'ID', fixed: 'left', sort: true},
                {field: 'order_number', title: '订单号', sort: true},
                {field: 'order_date', title: '订单日期', sort: true},
                {field: 'order_timestart', title: '订单开始时段'},
                {field: 'order_timeend', title: '订单结束时段'},
                {field: 'order_state', title: '订单状态'},
                {field: 'gym_price', title: '订单费用'},
                {field: 'account_name', title: '所属用户'},
                {field: 'gym_name', title: '所属场馆'},
                {field: 'site_number', title: '所属场地'},
                {title: '操作', fixed: 'right', toolbar: '#orderBar'}
            ]],
            page: true
        });

        //监听行工具事件
        table.on('tool(orderFilter)', function (obj) {
            var data = obj.data;
            var $ = layui.$;
            if (obj.event === 'cancel') {
                layer.confirm('确认取消吗？', function (index) {
                    $.get("<%=contextPath%>/order?action=cancel&order_number=" + data.order_number, function () {
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
