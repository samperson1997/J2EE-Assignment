<%--
  Created by IntelliJ IDEA.
  User: samperson1997
  Date: 2017/12/27
  Time: 00:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form method='POST'>
    login:<input type='text' name='login' value=''>
    password:<input type='password' name='password' value=''>
    <input type='submit' name='Submit' value='Submit'>
    <jsp:include page="numberStatistics.jsp"/>
</body>
</html>
