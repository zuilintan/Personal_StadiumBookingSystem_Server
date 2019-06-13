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
    <title>注销</title>
</head>
<body>
<%
    session.invalidate();
    request.getSession().setAttribute("msg", null);
    response.sendRedirect(contextPath + "/page/login.jsp");
%>
</body>
</html>
