<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Organizer - Sign up</title>

    <script src="/resources/js/jquery-1.10.2.min.js"></script>
    <script src="/resources/js/bootstrap.min.js"></script>
    <script src="/resources/js/bootstrap-select.js"></script>
    <script src="/resources/js/bootstrap-switch.js"></script>
    <script src="/resources/js/flatui-checkbox.js"></script>
    <script src="/resources/js/flatui-radio.js"></script>
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/css/flat-ui.css" rel="stylesheet">
    <link href="/resources/css/datepicker.css" rel="stylesheet">
    <link href="/resources/css/main.css" rel="stylesheet">

    <style>
        body {
            background-image: url('/resources/images/blur.jpg');
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: cover;
            margin: 0;
            padding: 55px 65px;
        }

        h1 {
            color: #FFFFFF;
            font-size: 72px;
        }

        h4 {
            color: #FFFFFF;
        }

        .row {
            position: relative;
            left: 10px;
            top: 10px;
        }
    </style>
</head>
<body>
<form:form id="login" cssClass="col-md-4 col-md-offset-4" action="edituser" commandName="userForm">
    <table width="100%">
        <tr>
            <td>
                <h3>Update profile</h3>
            </td>
            <td align="right">
                <a href="/protected" type="button" class="btn btn-info btn-lg">Back</a>
            </td>
        </tr>
    </table>
    <hr/>

    <c:if test="${not empty message}">
        <div class="alert alert-danger">${message}</div>
    </c:if>

    <spring:bind path="email">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:input path="email" class="input-lg form-control" placeholder="Email"  readonly="true"/>
            <form:errors path="email" cssClass="help-block" element="small" />
        </div>
    </spring:bind>

    <spring:bind path="name">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:input path="name"  class="input-lg form-control" placeholder="Name" />
            <form:errors path="name" cssClass="help-block" element="small" />
        </div>
    </spring:bind>

    <spring:bind path="surname">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:input path="surname" class="input-lg form-control" placeholder="Surname"/>
            <form:errors path="surname" cssClass="help-block" element="small" />
        </div>
    </spring:bind>

    <div class="form-group">
        <input type="checkbox" name="editecheckbox">Edit password
    </div>

    <spring:bind path="password">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:password path="password"  class="input-lg form-control" placeholder="Password" />
            <form:errors path="password" cssClass="help-block" element="small" />
        </div>
    </spring:bind>

    <spring:bind path="password">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:password path="matchingPassword" class="input-lg form-control" placeholder="Password confirmation" />
            <form:errors cssClass="help-block" element="small" />
        </div>
    </spring:bind>

    <div class="form-group" style="position: relative; top: 12px; padding-bottom: 12px">
        <input type="submit" class="btn btn-lg btn-info btn-block" value="Update" />
    </div>

</form:form>
<form:form id="login" cssClass="col-md-4 col-md-offset-4" action="deleteuser" commandName="userForm">
    <h3>Delete profile</h3>
    <hr/>
    <div class="form-group">
        <input type="checkbox" name="deletecheckbox">Yes, i agree.
    </div>
    <p><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /></p>
    <div class="form-group" style="position: relative; top: 12px; padding-bottom: 12px">
        <input type="submit" class="btn btn-lg btn-info btn-block" value="Delete" />
    </div>
</form:form>
</body>
</html>