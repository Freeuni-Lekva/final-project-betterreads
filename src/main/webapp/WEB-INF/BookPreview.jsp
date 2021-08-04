<%@ page import="Model.Book" %><%--
  Created by IntelliJ IDEA.
  User: Vano
  Date: 04.08.2021
  Time: 13:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<% Book book = (Book) request.getAttribute("book"); %>
<li>
    <%= book.getBook_name() %>
    <br>
    <%= book.getBook_description()%>
    <br>
    <img src=<%= book.getBook_photo()%> width="100" height="150">
</li>
</body>
</html>
