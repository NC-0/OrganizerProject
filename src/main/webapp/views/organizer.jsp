<%@ page language="java" contentType="text/html; charset=UTF-8"
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
    <script>
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            jqXHR.setRequestHeader(header, token);
        });
        function deleteCategory(cat) {
            if (cat != 0) {
                $.ajax({
                    type: "POST",
                    url: "/deletecategory",
                    data: {'categoryid': cat},
                    success: function () {
                        location.reload();
                    }
                });
            }
        }

        function deleteTask(id, sort) {
            $.ajax({
                type: "post",
                url: "task/delete",
                data: { 'id': id },
                success: function() {
                    selectTasks(sort);
                    /*location.reload();*/
                }
            });
        }

        function deleteSubtask(id, sort) {
            $.ajax({
                type: "post",
                url: "subtask/delete",
                data: { 'id': id },
                success: function() {
                    selectTasks(sort);
//                    location.reload();
                }
            });
        }

        function makeCategory(data) {
            $('#elements').empty();
            $('#elements').append('<li><div class="Item">' +
            '<a href="/addcategory" class="EditTask">' +
            '<span class="fui-plus"></span>Create</a></div></li>');

            $('#elements').append("<div class=\"Categories\">");
            $('#elements .Categories').append("<li><div class=\"Item\"><a style=\"cursor: pointer\" onclick='selectTasks(0)'><span class=\"fui-list\"></span>All</a></div></li>");
            for (var i = 0; i < data.length; i++) {
                $('#elements .Categories').append("<li><div class=\"Item\">" +
                        "<a href='/calendar?cat=" + data[i].id + "' class='EditTask'><span style='cursor: pointer' class='fui-calendar-solid'></span></a>"+
                        "<a href='/updatecategory?categoryid=" + data[i].id + "' class='EditTask'><span style='cursor: pointer' class='fui-gear'></span></a>" +
                        "<span style='cursor: pointer' class='fui-cross' onclick='deleteCategory(" + data[i].id + ")'></span>" +
                        "<a style=\"cursor: pointer\" onclick='selectTasks(" + data[i].id + ")' id=" + data[i].id + ">" + data[i].name + "</a>" +
                        "</div></li>");
                $('.clicktr').click(
                        $(function () {
                            $('#elements .Categories').on('click', 'li', function () {
                                $(this).parent().children().removeClass('Selected');
                                $(this).addClass('Selected');
                            });
                        })
                );
            }
            $('#elements').append("</table>")
        }

        function loadData() {
            $.ajax({
                type: "GET",
                url: "/categorylist",
                cache: false,
                async: true,
                dataType: 'json',
                success: function (data) {
                    makeCategory(data);
                }
            });
            selectTasks(0);
        }

        function selectTasks(cat) {
            /*$("#tasktable tbody tr").remove();*/
            if (cat == 0) {
                $.get("task/list", function (data) {
                    addRows(data, cat);
                });
            } else {
                $.get("task/listcat/" + cat, function (data) {
                    addRows(data, cat);
                });
            }
        }

        function addRows(data, sort) {
            $(".table-body").empty();
            $('#taskSize').text(data.length);
            $.each(data, function (i, task) {
                var date = new Date(task.date);
                $('.table-body').append(
                        "<div class='panel panel-default'>" +
                            "<div class='panel-heading' role='tab' id='heading" + task.id + "'>" +
                                "<a class='collapsed' role='button' data-toggle='collapse' data-parent='#accordion' href='#collapse" + task.id + "' aria-expanded='false' aria-controls='collapse'>" +
                                    "<div class='row'>" +
                                        "<div class='col-xs-1 Header'><input class='TaskSelAll' type='checkbox'></div>" +
                                        "<div class='col-xs-3 Header'>" + task.name + "</div>" +
                                        "<div class='col-xs-3 Header'>" + date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear() + "</div>" +
                                        "<div class='col-xs-2 Header'>" + task.priority_str + "</div>" +
                                        "<div class='col-xs-3 Header'>" +
                                            "<a href='subtask/create?id="+task.id+"' class='copy'>" +
                                                "<span style='cursor: pointer' class='fui-plus'></span>" +
                                            "</a>" +
                                            "<a href='task/edit?id="+task.id+"' class='copy'>" +
                                                "<span style='cursor: pointer' class='fui-gear'></span>" +
                                            "</a>" +
                                            "<a class='copy'>" +
                                                "<span style='cursor: pointer' class='fui-cross' onclick='deleteTask(" + task.id + "," + sort + ")' ></span>" +
                                            "</a>" +
                                        "</div>" +
                                    "</div>" +
                                "</a>" +
                            "</div>" +
                            "<div id='collapse"+task.id+"' class='panel-collapse collapse' role='tabpanel' aria-labelledby='heading'>" +
                            "</div>"
                );

                $("#collapse"+task.id).append("<div class='panel-body'>");
                $.get('subtask/list?id='+task.id, function (subtasks) {
                    $.each(subtasks, function (i, subtask) {
                        $("#collapse"+task.id).append(
                                "<div class='row'>" +
                                    "<div class='col-xs-1 Header'><input class='TaskSelAll' type='checkbox'></div>" +
                                    "<div class='col-xs-3 Header'>"+subtask.name+"</div>" +
                                    "<div class='col-xs-3 Header'></div>" +
                                    "<div class='col-xs-2 Header'></div>" +
                                    "<div class='col-xs-3 Header'>" +
                                        "<a href='subtask/edit?id="+subtask.id+"' class='copy'>" +
                                        "<span style='cursor: pointer; margin-left: 18px' class='fui-gear'></span>" +
                                        "</a>" +
                                        "<a class='copy'>" +
                                            "<span style='cursor: pointer' class='fui-cross' onclick='deleteSubtask(" + subtask.id + "," + sort + ")'></span>" +
                                        "</a>" +
                                    "</div>" +
                                "</div>"
                        );
                    });
                });
                $("collapse"+task.id).append("</div>");
                $('.table-body').append("</div>");
            });

        }

    </script>
</head>
<body onload="loadData()">

<table class="Body">
    <tr>
        <td class="Logo">
            <a href="">Organizer</a>
        </td>
        <td class="Action">
            Your tasks
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
                <div class="Title">Tasks</div>
                <ul>

                    <li>
                        <div class="Item">
                            <a href="/task/create" class="AddTask">
                                <span class="fui-plus"></span>
                                Create
                            </a>
                        </div>
                    </li>
                </ul>
                <div class="Title">Categories</div>
                <ul id="elements"></ul>
            </div>
        </td>
        <td class="Content" rowspan="2" colspan="2">
            <div class="Preambula">
                You have <span id='taskSize'></span> tasks.
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

