var userEvents="{\"events\":[";

$.ajaxPrefilter(function (options, originalOptions, jqXHR) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    jqXHR.setRequestHeader(header, token);
});

$(document).click(function(event){
    $('.popover.in').remove();
});

$(document).ready(function() {
    if(title=="")
        title='All tasks';
    else
        title='Category:'+title;
    if(cat==0){
        $.ajax({
            type: "GET",
            url: "task/list/false",
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
            },
            addTaskCustomButton: {
                text: 'Add task',
                click: function() {
                    window.location.href = '/task/create';
                }
            }
        },
        contentHeight: 600,
        header: {
            left: 'prev,next today',
            center: 'title addTaskCustomButton',
            right: 'backCustomButton'
        },
        titleFormat: '['+title+']',
        defaultDate: '2016-01-12',
        editable: false,
        eventLimit: true,
        eventSources:tmp,
        eventOrder: 'level',
        eventRender: function(event, element) {
            element.html('<table style="border: none" class="hovered"><tr>' +
                '<td onclick="clearPopover()" style=\'cursor: pointer;border: none\' id="task'+event.id+'" class="tasktitle">'+event.title + '</td>' +
                '<td style=\'border: none\' width="15px"><a href="subtask/create?id='+event.id+'" class="copy">' +
                '<span style="cursor: pointer" class="fui-plus"></span>' +
                '</a></td>' +
                '<td style=\'border: none\' width="15px"><a href="task/edit?id='+event.id+'" class="copy">' +
                '<span style="cursor: pointer" class="fui-gear"></span>' +
                '</a></td>' +
                '<td style=\'border: none\' width="15px"><a class="copy">' +
                '<span style=\'cursor: pointer\' class="fui-cross" onclick="deleteTask(' + event.id + ')"></span>' +
                '</a></td></tr>' +
                '</table>');
            var subtsk='<table class="table table-data TodoList"><tr><td colspan="4"><b>Subtasks:</td></tr>';
            $.ajax({
                type: "GET",
                url: "subtask/list?id=" + event.id,
                cache: false,
                async: false,
                dataType: 'json',
                success: function (data) {
                    for(var i=0;i<data.length;i++){
                        subtsk=subtsk+'<tr>' +
                            '<td><b>'+(i+1)+'.</b></td>' +
                            '<td>'+data[i].name+'</td>' +
                            '<td><a href="subtask/edit?id='+data[i].id+'" class="copy">' +
                            '<span style="cursor: pointer;" class="fui-gear"></span>' +
                            '</a></td>' +
                            '<td><a class="copy">' +
                            '<span style="cursor: pointer" class="fui-cross" onclick="deleteSubtask(' + data[i].id +')"></span>' +
                            '</a></td>' +
                            '</tr>';
                    }
                }
            });
            subtsk=subtsk+'</table>';
            element.popover({
                content: subtsk,
                html: true,
                placement: 'bottom',
                container:'body',
                trigger: 'focus'
            });
            element.attr('tabindex', -1);
        }
    });
});

function clearPopover(){
    event.stopPropagation();
}

function deleteTask(id) {
    $('.popover.in').remove();
    $.ajax({
        type: "post",
        url: "task/delete",
        data: { 'id': id },
        success: function(){
            location.reload();
        }
    });
}

function deleteSubtask(id) {
    $.ajax({
        type: "post",
        url: "subtask/delete",
        data: { 'id': id },
        success: function() {
            location.reload();
        }
    });
}

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
                "\",\"start\":\""+date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+
                "\",\"color\":\""+clr+
                "\",\"textColor\":\""+'#000000'+
                "\",\"level\":\""+data[i].priority+
                "\",\"id\":\""+data[i].id+"\"},";
        }
    }else{
        userEvents=userEvents+"[";
    }
}