﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
          pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Organizer</title>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <script src="/resources/js/jquery-1.10.2.min.js"></script>
    <script src="/resources/js/bootstrap.min.js"></script>
    <script src="/resources/js/bootstrap-select.js"></script>
    <script src="/resources/js/bootstrap-switch.js"></script>
    <script src="/resources/js/flatui-checkbox.js"></script>
    <script src="/resources/js/flatui-radio.js"></script>
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/css/flat-ui.css" rel="stylesheet">
    <link href="/resources/css/main.css" rel="stylesheet">
    <link href="/resources/css/style.css" rel="stylesheet">
    <link href="/resources/css/styles.css" rel="stylesheet">
    <script src="/resources/js/app/organizer.js"></script>

</head>

<script>
    var isArchived = true;
</script>

<body onload="loadData()">

<table class="Body">
    <tr>
        <td class="Logo">
            <a href="">Archive</a>
        </td>
        <td class="Action">
            Your completed tasks
        </td>
        <td class="Login">
            <a href='/calendar?cat=0' class='EditTask'><span style='cursor: pointer' class='fui-calendar-solid'></span></a>
            <a href='/updateprofile' class='EditTask'><span style='cursor: pointer' class='fui-gear'></span></a>
            ${username}&nbsp;${usersurname}<br/>
            <a href="j_spring_security_logout" class="Logout">Logout</a>
        </td>
    </tr>
    <tr>
        <td class="Sidebar">
            <div class="Fix">
                <div class="Title" onmousedown="return false" onselectstart="return false">Menu</div>
                <ul>
                    <li>
                        <div class="Item">
                            <a href="/" class="AddTask">
                                <span class="fui-list"></span>
                                Main page
                            </a>
                        </div>
                    </li>
                </ul>
                <div class="Title" onmousedown="return false" onselectstart="return false">Categories</div>
                <ul id="elements"></ul>
            </div>
        </td>
        <td class="Content" rowspan="2" colspan="2">
            <div class="Preambula" onmousedown="return false" onselectstart="return false">
                <div>You have <span id='taskSize'></span> tasks.</div>
                <input id="filterinput" type="text" onkeyup="doFilter(this.value)" size="40" class="input-lg form-control" placeholder="Filter">
            </div>
            <section id="tasktable" class="table table-data TodoList">

                <div class="data-tasks-js">
                    <!-- заголовочные столбцы таблицы, - неизменяемая строка таблицы -->
                    <div class="row table-header">
                        <div class="col-xs-1 Header"><input class='TaskSelAll' type='checkbox' disabled="true"></div>
                        <div class="col-xs-3 Header">Task name</div>
                        <div class="col-xs-3 Header">Date</div>
                        <div class="col-xs-2 Header">Priority</div>
                        <div class="col-xs-3 Header">Operations</div>
                    </div>
                    <div class='row table-body'>
                        <div class='panel-group accordion' id='accordion' role='tablist' aria-multiselectable='true'>  <!-- accordion begin  -->
                        </div>
                    </div>
                </div>
            </section>
        </td>
    </tr>
    <tr>
        <td class="Footer">
            &copy;&nbsp;Organizer Team, 2016
        </td>
    </tr>
</table>

</body>
</html>

