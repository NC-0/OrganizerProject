<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<title>Приветственная страница</title>
</head>
<body>
   <a href="/registration">Регистрация</a>
   <a href="/login">Вход</a>
   <a href="/updateprofile">Редактировать пользователя</a>
   <a href="/j_spring_security_logout">Выход</a>
   <a href="/protected">Защищенная страница</a>
   <a href="/createtask">Создание задачи</a>
    ${message}
</body>
</html>