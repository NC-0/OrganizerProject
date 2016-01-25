<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Organizer - Error</title>

    <script src="/resources/js/jquery-1.10.2.min.js"></script>
    <script src="/resources/js/bootstrap.min.js"></script>
    <script src="/resources/js/bootstrap-select.js"></script>
    <script src="/resources/js/bootstrap-switch.js"></script>
    <script src="/resources/js/flatui-checkbox.js"></script>
    <script src="/resources/js/flatui-radio.js"></script>
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/css/flat-ui.css" rel="stylesheet">
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

        .row {
            position: relative;
            left: 10px;
            top: 10px;
        }
    </style>
</head>
<body>
    <div style="width: 30%; margin:0 auto;" align="center">
        <form id="login">
            <table width="100%">
                <tr>
                    <td>
                        <h4>OPPS! Error ;(</h4>
                    </td>
                    <td align="right">
                        <a href="${url}" type="button" class="btn btn-info btn-lg">Back</a>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <hr>
                        <h2>${code}</h2>
                        ${message}
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>
