<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head></head>
<body>
<form action="edituser" method="post">
    <p>email:<input type="text" id="email" name="email" value="${email}" readonly></p>
    <p>password:<input type="password" id="password" name="password" ></p>
    <p>retry password:<input type="password" id="retrypassword" name="retrypassword" ></p>
    <p>name:<input type="text" id="name" value="${name}" name="name"></p>
    <p>surname:<input type="text" id="surname" value="${surname}" name="surname"></p>
    <p><input type="submit" value="update"><input type="reset" value="reset"></p>
</form>
</body>
</html>