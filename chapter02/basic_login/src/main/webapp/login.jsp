<%--
  Created by IntelliJ IDEA.
  User: sang
  Date: 2021/8/22
  Time: 01:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<form action="/doLogin" method="post">
    <input type="text" name="username">
    <input type="password" name="password">
    <input type="submit" value="登录">
</form>
</body>
</html>
