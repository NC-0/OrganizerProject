<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Organizer - Sign in</title>

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
<body onload='document.loginForm.username.focus();'>
    <form class="col-md-4 col-md-offset-4"
          id="login"
          name="loginForm"
          action="<c:url value='/j_spring_security_check' />"
          method="post">

        <h3>Sign in</h3>
        <hr/>

        <c:if test="${not empty error}">
            <div class="alert alert-danger"><small>${error}</small></div>
        </c:if>

        <div class="form-group">
            <input name="username" type="text" class="input-lg form-control" placeholder="Email">
        </div>
        <div class="form-group">
            <input name="password" type="password" class="input-lg form-control" placeholder="Password">
        </div>
        <div class="form-group" style="position: relative; top: 12px; padding-bottom: 12px">
            <input name="submit" type="submit" class="btn btn-lg btn-info btn-block" value="Log in">
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>
</body>
</html>