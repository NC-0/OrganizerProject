<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
    <style>
        .error {
            padding: 15px;
            margin-bottom: 15px;
            border: 1px solid transparent;
            border-radius: 4px;
            color: #a94442;
            background-color: #f2dede;
            border-color: #ebccd1;
            width:400px;
        }
        .attention {
            color: green;
        }
        .regborder {
            alignment: center;
            margin: 100px auto;
            padding: 15px;
        }
    </style>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
</head>
<body>
<div align="center" class="regborder">
    <c:if test="${not empty message}">
        <div class="error">${message}</div>
    </c:if>
    <table>
        <form:form action="createuser" commandName="userForm">
            <tr>
                <td>Email: </td>
                <td><form:input path="email" class="form-control" size="30"/></td>
                <td><form:errors path="email" cssClass="attention"/></td>
            </tr>
            <tr>
                <td>Password: </td>
                <td><form:password path="password" class="form-control" size="30"/></td>
                <td><form:errors path="password" cssClass="attention"/></td>
            </tr>
            <tr>
                <td>Retry password: </td>
                <td><form:password path="matchingPassword" class="form-control" size="30"/></td>
                <td><form:errors cssClass="attention"/></td>
            </tr>
            <tr>
                <td>Name: </td>
                <td><form:input path="name" class="form-control" size="30"/></td>
                <td><form:errors path="name" cssClass="attention"/></td>
            </tr>
            <tr>
                <td>Surname: </td>
                <td><form:input path="surname" class="form-control" size="30"/></td>
                <td><form:errors path="surname" cssClass="attention"/></td>
            </tr>
            <tr>
                <td></td>
                <td align="center"><input type="submit" class="btn btn-primary btn-sm" value="Register"/></td>
                <td></td>
            </tr>
        </form:form>
    </table>
</div>
</body>
</html>