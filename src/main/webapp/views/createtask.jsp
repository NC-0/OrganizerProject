<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>
<html>
<head>
	<title>Task creation</title>
	<style type="text/css">
		span.error {
			color: red;
		}
	</style>
</head>
<body>
<h1>Create task</h1>

<form:form method="post" commandName="taskForm" modelAttribute="taskForm">
	<table>
		<tr>
			<td>Name:</td>
			<td><form:input path="name" /></td>
			<td><form:errors path="name" cssClass="error" /></td>
		</tr>

		<tr>
			<td>Date:</td>
			<td><form:input path="date" type="date" /></td>
			<td><form:errors path="date" cssClass="error" /></td>
		</tr>

		<tr>
			<td>Priority :</td>
			<td><form:select path="priority_str">
				<form:option value="" label="--- Select ---" />
				<form:options items="${priorities}" />
			</form:select>
			<td><span class="error"><form:errors path="priority_str" /></span></td>
		</td>

		<tr>
			<td>Category :</td>
			<td><form:select path="category_str">
				<form:option value="" label="--- Select ---" />
				<form:options items="${categories}" />
			</form:select>
			<td><span class="error"><form:errors path="category_str" /></span></td>
		</td>

		<tr>
			<td colspan="3"><input type="submit" value="Submit" /></td>
		</tr>
	</table>
</form:form>

<a href="${pageContext.request.contextPath}/" title="Home">Home</a>
</body>
</html>