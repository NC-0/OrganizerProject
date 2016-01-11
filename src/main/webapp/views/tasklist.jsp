
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tasks list</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css"></head>
<body>

<h2>Tasks List</h2>

<table class="data-tasks-js table table-striped" >
    <tr>
        <th>Name</th>
        <th>Data</th>
        <th>Priority</th>
        <th>Status</th>
        <th>Category</th>
    </tr>
</table>

<button id="fetchTasks" class="btn btn-default" type="submit">Button</button>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script type="text/javascript">

    $("#fetchTasks").bind("click", function() {

        $.get("http://localhost:8081/task/list", function(data) {

            $.each(data, function(i, task) {

                $(".data-tasks-js").append(
                        "<tr><td>" + task.name + "</td>" +
                        "<td>" + task.data + "</td>" +
                        "<td>" + task.priority_str + "</td>" +
                        "<td>" + task.status + "</td>" +
                        "<td>" + task.category_str + "</td></tr>");
            });

        });
    });
</script>
</body>
</html>