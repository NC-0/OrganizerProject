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
    <style>
        .error {
            color: red; font-weight: bold;
        }
    </style>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>

</head>
<body>
<a class="btn btn-primary btn-lg" href="/categorylist" >Мои категории</a>
<a class="btn btn-primary btn-lg" href="/createtask">Создание задачи</a>
<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">Новая категория</button>

<table>
    <form:form action="createcategory" commandName="categoryForm">
    <tr>
        <td colspan="3" align="left"><form:errors path="name" cssClass="error"/></td>
    </tr>
    <tr>
        <td width="20%">Category title: </td>
        <td width="40%"><form:input path="name" size="30"/></td>
        <td><input type="submit" value="Add"/></td>
    </tr>
    </form:form>
</table>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
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
                            <td width="20%">Category title: </td>
                            <td width="40%"><form:input path="name" class="form-control" size="30"/></td>
                            <td><input class="btn btn-primary" type="submit" value="Add"/></td>
                        </tr>
                    </form:form>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
