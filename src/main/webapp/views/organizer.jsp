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
    <link href="/resources/css/datepicker.css" rel="stylesheet">
    <link href="/resources/css/main.css" rel="stylesheet">
    <link href="/resources/css/style.css" rel="stylesheet">

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
        function makeCategory(data) {
            $('#elements').empty();
            $('#elements').append('<li><div class="Item">' +
                    '<a href="/addcategory" class="EditTask">' +
                    '<span class="fui-plus"></span>Create</a></div></li>');

            $('#elements').append("<div class=\"Categories\">");
            $('#elements .Categories').append("<li><div class=\"Item\"><a style=\"cursor: pointer\" onclick='selectTasks(0)'><span class=\"fui-list\"></span>All</a></div></li>");
            for (var i = 0; i < data.length; i++) {
                $('#elements .Categories').append("<li><div class=\"Item\">" +
                        "<a href='/updatecategory?categoryid=" + data[i].id + "&categoryname="+data[i].name+"' class='EditTask'><span style='cursor: pointer' class='fui-gear'></span></a>" +
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
            $("#tasktable tbody tr").remove();
            if (cat == 0) {
                $.get("task/list", function (data) {
                    $(".data-tasks-js").empty();

                    <!-- accordion begin -->
                    $(".data-tasks-js").append(
                            "<table class='table TodoList'>"+
                            "<tr>" +
                            "<th class='Header' style='width: 1px'><input class='TaskSelAll' type='checkbox'></th>" +
                            "<th class='Header' style='min-width: 14em;'>Task name</th>" +
                            "<th class='Header' style='min-width: 10em;'>Date</th>" +
                            "<th class='Header' style='min-width: 10em;'>Priority</th>" +
                            "<th class='Header' style='min-width: 10em;'>Category</th>" +
                            "<th></th>" +
                            "</tr>" +
                            "</table>" +

                            "<tr>" +
                            "<div class='panel-group accordion' id='accordion' role='tablist' aria-multiselectable='true'>"
                    );

                    $.each(data, function (i, task) {
                        var date = new Date(task.date);

                        $('.data-tasks-js').append(


                                "<div class='panel panel-default'>" +
                                "<div class='panel-heading' role='tab' id='heading'>" +
                                "<h4 class='panel-title'>" +
                                "<a class='collapsed' role=\"button\" data-toggle='collapse' data-parent='#accordion' href='#collapse' aria-expanded=\"false\" aria-controls=\"collapseThree\">" +
                                "<table class='table TodoList'>"+
                                "<tbody>" +
                                "<tr>" +
                                "<td class='Header' style='width: 1px'>" +
                                "<input type=\"checkbox\" " + (task.completed ? "checked" : "") + "/>" +
                                "</td>" +
                                "<td class='Header' style='min-width: 14em;'>" + task.name + "</td>" +
                                "<td class='Header' style='min-width: 10em;'>" + date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear() + "</td>" +
                                "<td class='Header' style='min-width: 10em;'>" + task.priority + "</td>" +
                                "<td class='Header' style='min-width: 10em;'>" +
//                                                        "<div class=\"btns pull-right\">" +
                                "<a href='task/edit?="+task.id+"' class='copy'><span style='cursor: pointer' class='fui-gear'></span></a>" +
                                "<a href='task/delete?="+task.id+"' class='delete'><span style='cursor: pointer' class='fui-cross'></span></a>" +
//                                                        "</div>" +
                                "</td></tr>" +
                                "</tbody>" +
                                "</table>" +
                                "</a>" +
                                "</h4>" +
                                "</div>" +
                                "<div id='collapse' class='panel-collapse collapse' role='tabpanel' aria-labelledby='heading'>" +
                                "<div class='panel-body'>" +
                                "<div class='text pull-left'>" +
                                "Subtasks" +
                                "</div>" +
                                "<div class=\"btns pull-right\">" +
                                "<a href='subtask/edit' class='copy'><span style='cursor: pointer' class='fui-gear'></span></a>" +
                                "<a href='subtask/delete' class='delete'><span style='cursor: pointer' class='fui-cross'></span></a>" +
                                "</div>" +
                                "</div>" +
                                "</div>" +
                                "</div>"
                        );
                    });

                    <!-- accordion end -->
                    $(".data-tasks-js").append(
                            "</div>" +
                            "</tr>"
                    );

                });
            } else {
                $.get("task/listcat/" + cat, function (data) {

                    $.each(data, function (i, task) {
                        var date = new Date(task.date);

                        $(".data-tasks-js").append(
                                "<tr>" +
                                "<td style='padding-left: 15px'>" +
                                "<input type=\"checkbox\" " + (task.completed ? "checked" : "") + "/>" +
                                "</td>" +
                                "<td>" + task.name + "</td>" +
                                "<td>" + date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear() + "</td>" +
                                "<td>" + task.priority + "</td>" +
                                "<td>" + task.category + "</td></tr>"
                        );
                    });

                });
            }
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
                            <a href="task/edit">
                                <span class="fui-plus"></span>
                                Edit
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
                You have <span>N</span> tasks.
            </div>

            <table id="tasktable" class="table TodoList">

                <tbody class="data-tasks-js"></tbody>
            </table>
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
