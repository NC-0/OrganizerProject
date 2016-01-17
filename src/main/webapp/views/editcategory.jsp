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
<form:form id="login" action="editcategory" cssClass="col-md-4 col-md-offset-4" commandName="categoryForm" >
    <table width="100%">
        <tr>
            <td>
                <h3>Edit category</h3>
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

    <spring:bind path="name">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:input path="name" id="editnamefield" class="input-lg form-control" placeholder="Name" />
            <form:errors path="name" cssClass="help-block" element="small" />
        </div>
    </spring:bind>

    <spring:bind path="id">
        <form:input type="hidden" path="id" id="editidfield" class="form-control" size="50"/>
    </spring:bind>

    <div class="form-group" style="position: relative; top: 12px; padding-bottom: 12px">
        <input type="submit" class="btn btn-lg btn-info btn-block" value="Submit" />
    </div>
</form:form>
</body>
</html>