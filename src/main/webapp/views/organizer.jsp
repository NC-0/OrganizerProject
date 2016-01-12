<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Organizer</title>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <style>
        .error {
            color: red; font-weight: bold;
        }

        #elements{
            position: absolute;
            height: 200px;
            overflow-y: scroll ;
            overflow-x: hidden;
            border: solid 1px lightgray;
            border-radius: 5px;
            position: absolute;
            float: left;
        }

        #elements td{
            text-align: left;
            height: 40px;
        }

        #elements td:hover {
            cursor: pointer;
            cursor: hand;
            background: #79c4e0;
        }

        .active{
            background-color: #7DAFFF;
        }

        .delete{
            text-align: left;
            cursor: hand;
            width: 10px;
            height:20px;
            background-color: #ffffff;
        }

        .delete:hover {
            text-align: left;
            cursor: hand;
            background-color:#e01f00;
            width: 10px;
            height:20px;
        }

        .edit{
            text-align: left;
            cursor: hand;
            width: 10px;
            height:20px;
            background-color: #ffffff;
        }

        .edit:hover{
            text-align: left;
            cursor: hand;
            background-color: #5ce05a;
            width: 10px;
            height:20px;
        }

        #parent {
            width: 1080px; margin: auto;
            position: relative;
        }

        #content {
            border: solid 1px lightgray;
            border-radius: 5px;
            position: absolute;
            left: 230px;
            width: 900px;
        }
    </style>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
    <script>
        $.ajaxPrefilter(function(options, originalOptions, jqXHR) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            jqXHR.setRequestHeader(header, token);
        });
        function deleteCategory(cat){
            if(cat!=0) {
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
        function editCategory(cat){
            $('#editidfield').val(cat);
            $('#editnamefield').val($('#'+cat).text());
        }
        function makeCategory(data){
            $('#elements').empty();
            $('#elements').append("<table>")
            $('#elements table').append("<tr><td onclick='selectTasks(0)' class='clicktr'>All</td></tr> ")
            for(var i=0;i<data.length;i++) {
                $('#elements table').append("<tr><td width='200px' onclick='selectTasks("+data[i].id+")' class='clicktr' id=" + data[i].id + ">" + data[i].name + "</td><td><div class='delete' onclick='deleteCategory("+data[i].id+")'>D</div><div data-toggle=\"modal\" data-target=\"#myModal1\" class='edit' onclick='editCategory("+data[i].id+")'>U</div></td></tr>");
                $('.clicktr').click(
                        $(function(){
                            $('#elements table').on('click', 'tr', function(){
                                $(this).parent().children().removeClass('active');
                                $(this).addClass('active');
                            });
                        })
                );
            }
            $('#elements').append("</table>")
        }
        function loadData(){
            $.ajax({
                type: "GET",
                url: "/categorylist",
                cache:false,
                async:true,
                dataType :'json',
                success: function(data){
                    makeCategory(data);
                }
            });
        }
        function selectTasks(cat){
            $("#tasktable tbody tr").remove();
            if(cat==0){
                $.get("http://localhost:8081/task/list", function(data) {

                    $.each(data, function(i, task) {
                        var date = new Date(task.date);

                        $(".data-tasks-js").append(
                                "<tr><td>" + task.name + "</td>" +
                                "<td>" + date + "</td>" +
                                "<td>" + task.priority + "</td>" +
                                "<td>" + task.completed + "</td>" +
                                "<td>" + task.category + "</td></tr>"
                        );
                    });

                });
            }else{
                $.get("http://localhost:8081/task/listcat/"+cat, function(data) {

                    $.each(data, function(i, task) {
                        var date = new Date(task.date);

                        $(".data-tasks-js").append(
                                "<tr><td>" + task.name + "</td>" +
                                "<td>" + date + "</td>" +
                                "<td>" + task.priority + "</td>" +
                                "<td>" + task.completed + "</td>" +
                                "<td>" + task.category + "</td></tr>"
                        );
                    });

                });
            }
        }
    </script>
</head>
<body onload="loadData()">
<button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal">Новая категория</button>
<!-- Modal -->
<div class="modal fade bs-example-modal-md" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" da>
    <div class="modal-dialog modal-md" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Add category</h4>
            </div>
            <div class="modal-body">
                <table>
                    <form:form action="createcategory" commandName="categoryForm">
                        <tr>
                            <td colspan="3" align="left"><form:errors path="name" cssClass="error"/></td>
                        </tr>
                        <tr>
                            <td>Category title: </td>
                            <td><form:input path="name" class="form-control" size="50"/></td>
                            <td><input class="btn btn-primary" type="submit" value="Submit"/></td>
                        </tr>
                    </form:form>
                </table>
            </div>
        </div>
    </div>
</div>
<div class="modal fade bs-example-modal-md" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" da>
    <div class="modal-dialog modal-md" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Edit category</h4>
            </div>
            <div class="modal-body">
                <table>
                    <form:form action="editcategory" commandName="categoryForm">
                        <tr>
                            <td colspan="3" align="left"><form:errors path="name" cssClass="error"/></td>
                        </tr>
                        <tr>
                            <td colspan="3" align="left"><form:input type="hidden" path="id" id="editidfield" class="form-control" size="50"/></td>
                        </tr>
                        <tr>
                            <td>Category title: </td>
                            <td><form:input path="name" class="form-control" id="editnamefield" size="50"/></td>
                            <td><input class="btn btn-primary" type="submit" value="Submit"/></td>
                        </tr>
                    </form:form>
                </table>
            </div>
        </div>
    </div>
</div>
<br><br><br>
<div id="parent">
    Categories
    <div id="elements"></div>
    <div id="content">
        <table id="tasktable" class="data-tasks-js table table-striped" >
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Data</th>
                    <th>Priority</th>
                    <th>Status</th>
                    <th>Category</th>
                </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>
