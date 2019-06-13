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
    <title>场馆列表</title>
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" media="all" href="<%=contextPath%>/asset/layui/css/layui.css">
    <link rel="stylesheet" media="all" href="<%=contextPath%>/asset/common.css">
</head>
<body onload="disablePageBack()">

<table class="layui-hide" id="gymTable" lay-filter="gymFilter"></table>

<script src="<%=contextPath%>/asset/layui/layui.js"></script>
<script type="text/html" id="gymBar">
    <a class="layui-btn layui-btn-xs" lay-event="switch">切换状态</a>
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
            elem: '#gymTable',
            height: 'full-0',
            url: '<%=contextPath%>/gym?action=showall',
            cellMinWidth: 80,
            cols: [[
                {field: 'gym_id', title: 'ID', fixed: 'left', sort: true},
                {field: 'gym_name', title: '场馆名'},
                {field: 'gym_price', title: '费用', sort: true},
                {field: 'gym_managestate', title: '管理状态', sort: true},
                {field: 'gym_opentime', title: '开馆时间'},
                {field: 'gym_closetime', title: '闭馆时间'},
                {title: '操作', fixed: 'right', toolbar: '#gymBar'}
            ]],
            page: true
        });

        //监听行工具事件
        table.on('tool(gymFilter)', function (obj) {
            var data = obj.data;
            var $ = layui.$;
            if (obj.event === 'switch') {
                layer.confirm('确认切换状态吗？', function (index) {
                    if (data.gym_managestate === 0) {
                        $.get("<%=contextPath%>/gym?action=operate&gym_name=" + data.gym_name, function () {
                            tableRender.reload();
                        });
                        layer.msg('操作完成');
                        layer.close(index);
                    } else {
                        $.get("<%=contextPath%>/gym?action=vindicate&gym_name=" + data.gym_name, function () {
                            tableRender.reload();
                        });
                        layer.msg('操作完成');
                        layer.close(index);
                    }
                });
            }
        });
    });
</script>
</body>
</html>
