<%@ page import="organizer.models.Category" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        List<Category> list =(List<Category>) request.getAttribute("catList");
        for(int i=0;i<list.size();i++)
            out.println(list.get(i).getName());
    %>
</body>
</html>
