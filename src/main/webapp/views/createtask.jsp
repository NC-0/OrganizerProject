<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
	<title>Create task</title>

	<script src="/resources/js/jquery-1.10.2.min.js"></script>
	<script src="/resources/js/bootstrap.min.js"></script>
	<script src="/resources/js/bootstrap-select.js"></script>
	<script src="/resources/js/bootstrap-switch.js"></script>
	<script src="/resources/js/flatui-checkbox.js"></script>
	<script src="/resources/js/flatui-radio.js"></script>
	<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
	<link href="/resources/css/flat-ui.css" rel="stylesheet">
	<%--<link href="/resources/css/datepicker.css" rel="stylesheet">--%>
	<link href="/resources/css/main.css" rel="stylesheet">

	<%--<link href="/resources/css/bootstrap.min.css" rel="stylesheet">--%>
	<%--<script src="http://code.jquery.com/jquery-1.10.2.js"></script>--%>
	<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">

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

		.input-lg {
			/*margin-bottom: 12px;*/
		}

		.help-block {
			margin-left: 15px;
		}
	</style>

</head>
<body onload='document.taskForm.name.focus();'>

	<form:form name="taskForm"
			method="post"
			commandName="taskForm"
			modelAttribute="taskForm"
			class="col-md-4 col-md-offset-4"
			id="login">

		<table width="100%">
			<tr>
				<td>
					<h3>Create task</h3>
				</td>
				<td align="right">
					<a href="/protected" type="button" class="btn btn-info btn-lg">Back</a>
				</td>
			</tr>
		</table>
		<hr/>

		<spring:bind path="name">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<form:input path="name" class="input-lg form-control" placeholder="Name" />
				<form:errors path="name" cssClass="help-block" element="small" />
			</div>
		</spring:bind>

		<spring:bind path="date">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<form:input path="date"  class="input-lg form-control" id="dateid" placeholder="Date" />
				<form:errors path="date" cssClass="help-block" element="small" />
			</div>
		</spring:bind>

		<spring:bind path="priority_str">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<select name="priority_str" class="form-control">
					<c:forEach  var="prior" items="${priorities}">
						<option value="${prior}"
								<c:if test="${prior == priority}">
									selected="selected"
								</c:if> >
								${prior}
						</option>
					</c:forEach>
				</select>
				<form:errors path="priority_str" cssClass="help-block" element="small" />
			</div>
		</spring:bind>

		<spring:bind path="category_str">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<select name="category_str" class="form-control">
					<c:forEach  var="cat" items="${categories}">
						<option value="${cat}"
								<c:if test="${cat == category}">
									selected="selected"
								</c:if> >
								${cat}
						</option>
					</c:forEach>
				</select>
				<form:errors path="category_str" cssClass="help-block" element="small" />
			</div>
		</spring:bind>

		<div class="form-group" style="position: relative; top: 12px; padding-bottom: 12px">
			<input type="submit" value="Submit" class="btn btn-lg btn-info btn-block" />
		</div>
	</form:form>
</body>

<script>
	$(document).ready(function() {
		$(function() {
			$("#dateid").datepicker({
				dateFormat: "dd/mm/yy"
			});
		});
	});
</script>
</html>