<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Organizer - calendar</title>
    <link href="/resources/css/main.css" rel="stylesheet">
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
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
    <link rel='stylesheet' href='/resources/js/fullcalendar/fullcalendar.css' />
    <script src='/resources/js/fullcalendar/lib/jquery.min.js'></script>
    <script src='/resources/js/fullcalendar/lib/moment.min.js'></script>
    <script src='/resources/js/fullcalendar/fullcalendar.js'></script>
    <script>
        var userEvents="{\"events\":[";
        $(document).ready(function() {
            var cat = ${cat};
            if(cat==0){
                $.ajax({
                    type: "GET",
                    url: "task/list",
                    cache: false,
                    async: false,
                    dataType: 'json',
                    success: function (data) {
                        parseDate(data);
                    }
                });
            }else{
                $.ajax({
                    type: "GET",
                    url: "task/listcat/" + cat,
                    cache: false,
                    async: false,
                    dataType: 'json',
                    success: function (data) {
                        parseDate(data);
                    }
                });
            }
            userEvents= userEvents.substring(0,userEvents.length - 1);
            userEvents=userEvents+"]}";
            var tmp = JSON.parse(userEvents);
            $('#calendar').fullCalendar({
                customButtons: {
                    backCustomButton: {
                        text: 'Back',
                        click: function() {
                            window.location.href = '/';
                        }
                    }
                },
                contentHeight: 600,
                header: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'backCustomButton'
                },
                defaultDate: '2016-01-12',
                editable: false,
                eventLimit: true,
                eventSources:tmp
            });
           });
        function parseDate(data){
            if(data.length!=0){
                for (var i = 0; i < data.length; i++) {
                    var date = new Date(data[i].date);
                    var clr;
                    switch(data[i].priority){
                        case 1:
                            clr = '#fe5537';
                            break;
                        case 2:
                            clr = '#FF9933';
                            break;
                        case 3:
                            clr = '#fedf38';
                            break;
                        case 4:
                            clr = '#1CE333';
                            break;
                        case 5:
                            clr = '#33CCFF';
                            break;
                        default:
                            clr = '#00FA9A';
                            break;
                    }
                    userEvents=userEvents+"{\"title\":\""+data[i].name+
                            "\",\"start\":\""+date.getFullYear()+"-"+date.getMonth()+1+"-"+date.getDate()+
                            "\",\"color\":\""+clr+
                            "\",\"textColor\":\""+'#000000'+"\"},";
                }
            }else{
                userEvents=userEvents+"[";
            }
        }
    </script>
</head>
<body>
<form id="login">
    <div id='calendar'></div>
</form>
</body>
</html>
