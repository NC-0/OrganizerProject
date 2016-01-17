<%@ page contentType="text/html; charset=UTF-8" %>


<html>
<head>
</head>
<title>Organizer</title>
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
        background-image: url('/resources/images/screen.jpg');
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

    #bottomalign{
        top: 0px;
        left: 0px;
        width:100%;
        position: absolute;
    }

    .bottom1{
        background-color: #34495E;
        padding: 10px;
        color: #fff9fb;
    }

    .mesg{
        padding-right: 10px;
        position: absolute;
        right: 0px;
        color: #fff6fc;
    }

    #logodiv{
        left: 10px;
        position: absolute;
    }
</style>
</head>
<body>

<div id="bottomalign">
   <div class="bottom1">
       <div class="mesg">${message}</div>
       <a style="color: #ffffff" href="/registration">Registration</a>&nbsp;&nbsp;&nbsp;<a style="color: #ffffff" href="/login">Sign in</a>
   </div>
</div>

<div id="logodiv">
    <table><tr><td><a href=""><img src="/resources/images/logo.png"></a></td><td class="logo"><h3>Organizer</h3></td></tr></table>
</div>

</body>
</html>