﻿<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<title>Приветственная страница</title>
</head>
<body>
   <a href="/registration">Регистрация</a>
   <a href="/login">Вход</a>
   <a href="/j_spring_security_logout">Выход</a>
   <a href="/protected">Защищенная страница</a>
    ${message}
</body>
</html>