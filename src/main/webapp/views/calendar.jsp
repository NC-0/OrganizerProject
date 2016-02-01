<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Organizer - calendar</title>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <link href="/resources/css/flat-ui.css" rel="stylesheet">
    <link href="/resources/css/main.css" rel="stylesheet">
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/css/styles.css" rel="stylesheet">

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

        .tasktitle{
            overflow: hidden;
        }

    </style>
    <link rel='stylesheet' href='/resources/js/fullcalendar/fullcalendar.css' />
    <script src='/resources/js/fullcalendar/lib/jquery.min.js'></script>
    <script src='/resources/js/fullcalendar/lib/moment.min.js'></script>
    <script src='/resources/js/fullcalendar/fullcalendar.js'></script>
    <script src="/resources/js/bootstrap.min.js"></script>
    <script>
        var title = '${cattitle}';
        var cat = ${cat};
    </script>
    <script src="/resources/js/app/calendar.js"></script>
</head>
<body>
<form id="login">
    <div id='calendar' style="font-size: 16px !important;"></div>
</form>
</body>
</html>
