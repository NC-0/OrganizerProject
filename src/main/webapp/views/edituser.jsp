<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
    <style>
        .error {
            color: red; font-weight: bold;
        }
    </style>
</head>
<body>
<div align="center">
    <div class="error">${message}</div>
    <table border="0" width="90%">
        <form:form action="edituser" commandName="userForm">
            <tr>
                <td align="left" width="20%">Email: </td>
                <td align="left" width="40%"><form:input path="email" size="30" readonly="true"/></td>
                <td align="left"><form:errors path="email" cssClass="error"/></td>
            </tr>

            <tr>
                <td>Name: </td>
                <td><form:input path="name" size="30"/></td>
                <td><form:errors path="name" cssClass="error"/></td>
            </tr>
            <tr>
                <td>Surname: </td>
                <td><form:input path="surname" size="30"/></td>
                <td><form:errors path="surname" cssClass="error"/></td>
            </tr>
            <tr>
                <td colspan="3"><input type="checkbox" name="editecheckbox">Edit password</td>
            </tr>
            <tr>
                <td>Password: </td>
                <td><form:password path="password" size="30"/></td>
                <td><form:errors path="password" cssClass="error"/></td>
            </tr>
            <tr>
                <td>Retry password: </td>
                <td><form:password path="matchingPassword" size="30"/></td>
                <td><form:errors cssClass="error"/></td>
            </tr>
            <tr>
                <td></td>
                <td align="center"><input type="submit" value="Update"/></td>
                <td></td>
            </tr>
        </form:form>
    </table>
</div>
<form action="deleteuser" method="post">
    <p>Delete user:<input type="checkbox" name="deletecheckbox">Yes, i agree.</p>
    <p><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /></p>
    <p><input type="submit" value="delete"></p>
</form>
</body>
</html>