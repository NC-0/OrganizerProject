$.ajaxPrefilter(function (options, originalOptions, jqXHR) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    jqXHR.setRequestHeader(header, token);
});

var filterids = [];

function deleteCategory(cat) {
    if (cat != 0) {
        $.ajax({
            type: "POST",
            url: "/deletecategory",
            data: {'categoryid': cat},
            success: function () {
                location.reload();
            }
        });
    }
}

function completeTask(id) {
    $.ajax({
        type: "post",
        url: "task/complete",
        data: {
            'id': id,
            'completed': isArchived
        },
        success: function() {
            $("#collapse"+id).animate({'line-height':0},1000).fadeOut(1000,function() {
                $("#collapse"+id).remove();
            });
            $("#heading"+id).animate({'line-height':0},1000).fadeOut(1000,function() {
                $("#heading"+id).remove();
            });
        }
    });
}



function deleteTask(id) {
    $.ajax({
        type: "post",
        url: "task/delete",
        data: { 'id': id },
        success: function() {
            $("#collapse"+id).animate({'line-height':0},1000).fadeOut(1000,function() {
                $("#collapse"+id).remove();
            });
            $("#heading"+id).animate({'line-height':0},1000).fadeOut(1000,function() {
                $("#heading"+id).remove();
            });
        }
    });
}

function deleteSubtask(id) {
    $.ajax({
        type: "post",
        url: "subtask/delete",
        data: { 'id': id },
        success: function() {
            $("#subtask"+id).animate({'line-height':0},1000).fadeOut(1000,function() {
                $("#subtask"+id).remove();
            });
        }
    });
}

function makeCategory(data) {
    $('#elements').empty();
    //$('#elements').append('<li><div class="Item">' +
    //'<a href="/addcategory" class="EditTask">' +
    //'<span class="fui-plus"></span>Create</a></div></li>');

    $('#elements').append("<div class=\"Categories\">");
    $('#elements .Categories').append("<li><div class=\"Item\"><a style=\"cursor: pointer\" onclick='selectTasks(0)'><span class=\"fui-list\"></span>All</a></div></li>");
    for (var i = 0; i < data.length; i++) {
        $('#elements .Categories').append("<li><div class=\"Item\">" +
        "<a href='/calendar?cat=" + data[i].id + "' class='EditTask'><span style='cursor: pointer' class='fui-calendar-solid'></span></a>"+
        "<a href='/updatecategory?categoryid=" + data[i].id + "' class='EditTask'><span style='cursor: pointer' class='fui-gear'></span></a>" +
        "<span style='cursor: pointer' class='fui-cross' onclick='deleteCategory(" + data[i].id + ")'></span>" +
        "<a style=\"cursor: pointer\" onclick='selectTasks(" + data[i].id + ")' id=" + data[i].id + ">" + data[i].name + "</a>" +
        "</div></li>");
        $('.clicktr').click(
            $(function () {
                $('#elements .Categories').on('click', 'li', function () {
                    $(this).parent().children().removeClass('Selected');
                    $(this).addClass('Selected');
                });
            })
        );
    }
    $('#elements').append("</table>")
}

function loadData() {
    $.ajax({
        type: "GET",
        url: "/categorylist",
        cache: false,
        async: true,
        dataType: 'json',
        success: function (data) {
            makeCategory(data);
        }
    });
    selectTasks(0);
}

function selectTasks(cat) {
    if (cat == 0) {
        $.get("task/list?status="+isArchived, function (data) {
            addRows(data, cat);
        });
    } else {
        $.get("task/listcat?status="+isArchived + "&cat=" + cat, function (data) {
            addRows(data, cat);
        });
    }
}

function addRows(data, sort) {
    $(".table-body").empty();
    $('#taskSize').text(data.length);
    $.each(data, function (i, task) {
        var date = new Date(task.date);
        var checked = "";
        if (task.completed) checked = "checked"
        filterids.push('#filtereddiv'+i);
        $('.table-body').append(
            "<div id='filtereddiv"+i+"' class='panel panel-default'>" +
            "<div class='panel-heading' role='tab' id='heading" + task.id + "'>" +
            "<a class='collapsed' role='button' data-toggle='collapse' data-parent='#accordion' href='#collapse" + task.id + "' aria-expanded='false' aria-controls='collapse'>" +
            "<div class='row'>" +
            "<div class='col-xs-1 Header'>" +
            "<input class='TaskSelAll' type='checkbox' onclick='completeTask(" + task.id + ")'" + checked +">" +
            "</div>" +
            "<div id='filteredtext"+i+"' class='col-xs-3 Header'>" + task.name + "</div>" +
            "<div class='col-xs-3 Header'>" + date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear() + "</div>" +
            "<div class='col-xs-2 Header'>" + task.priority_str + "</div>" +
            "<div class='col-xs-3 Header'>" +
            "<a href='subtask/create?id="+task.id+"' class='copy'>" +
            "<span style='cursor: pointer' class='fui-plus'></span>" +
            "</a>" +
            "<a href='task/edit?id="+task.id+"' class='copy'>" +
            "<span style='cursor: pointer' class='fui-gear'></span>" +
            "</a>" +
            "<a class='copy'>" +
            "<span style='cursor: pointer' class='fui-cross' onclick='deleteTask(" + task.id + ")' ></span>" +
            "</a>" +
            "</div>" +
            "</div>" +
            "</a>" +
            "</div>" +
            "<div id='collapse"+task.id+"' class='panel-collapse collapse' role='tabpanel' aria-labelledby='heading'>" +
            "</div>"
        );

        $("#collapse"+task.id).append("<div class='panel-body'>");
        $.get('subtask/list?id='+task.id, function (subtasks) {
            $.each(subtasks, function (i, subtask) {
                $("#collapse"+task.id).append(
                    "<div class='row' id='subtask"+subtask.id+"'>" +
                    "<div class='col-xs-1 Header'><input class='TaskSelAll' type='checkbox'></div>" +
                    "<div class='col-xs-3 Header'>"+subtask.name+"</div>" +
                    "<div class='col-xs-3 Header'></div>" +
                    "<div class='col-xs-2 Header'></div>" +
                    "<div class='col-xs-3 Header'>" +
                    "<a href='subtask/edit?id="+subtask.id+"' class='copy'>" +
                    "<span style='cursor: pointer; margin-left: 18px' class='fui-gear'></span>" +
                    "</a>" +
                    "<a class='copy'>" +
                    "<span style='cursor: pointer' class='fui-cross' onclick='deleteSubtask(" + subtask.id +")'></span>" +
                    "</a>" +
                    "</div>" +
                    "</div>"
                );
            });
        });
        $("collapse"+task.id).append("</div>");
        $('.table-body').append("</div>");
    });

}

function doFilter(filtertext){
    for(var i=0;i<filterids.length;i++) {
        var txt = $('#filteredtext' + i).text().toLowerCase();
        if (txt.indexOf(filtertext.toLowerCase()) + 1) {
            $(filterids[i]).css('display','block');
        }else{
            $(filterids[i]).css('display','none');
        }
    }
}
