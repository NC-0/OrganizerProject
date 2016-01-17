<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
	<link href="/resources/css/datepicker.css" rel="stylesheet">
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

		error {
			color: red;
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

		<div class="form-group">
			<form:input path="name" class="input-lg form-control" placeholder="Name" />
			<form:errors path="name" cssClass="error" />
		</div>

		<div class="form-group">
			<form:input path="date"  class="input-lg form-control" type="text" id="dateid" placeholder="Date" />
			<form:errors path="date" cssClass="error" />
		</div>

		<div class="form-group">
			<form:select path="priority_str" class="form-control">
				<option selected>Open to choose priority:</option>
				<form:options items="${priorities}" />
			</form:select>
			<span class="error"><form:errors path="priority_str" /></span>

		</div>

		<div class="form-group">
			<form:select path="category_str" class="form-control">
				<option selected>Choose category:</option>
				<form:options items="${categories}" />
			</form:select>
			<span class="error"><form:errors path="category_str" /></span>
		</div>

		<div class="form-group" style="position: relative; top: 12px; padding-bottom: 12px">
			<input type="submit" value="Submit" class="btn btn-lg btn-info btn-block" />
		</div>
	</form:form>
</body>

<script>
	$(document).ready(function() {
		$(function() {
			$("#dateid").datepicker({
				dateFormat: "dd-mm-yy"
			});
		});

	});
</script>
</html>