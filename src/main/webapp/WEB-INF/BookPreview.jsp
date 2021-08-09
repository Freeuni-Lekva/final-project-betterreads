<%@ page import="Model.Book" %>
<%@ page import="Service.AllServices" %>
<%@ page import="Constants.SharedConstants" %>
<%@ page import="Service.DescriptionShortener" %><%--
  Created by IntelliJ IDEA.
  User: Vano
  Date: 04.08.2021
  Time: 13:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="/Style/PreviewStyle.css" type="text/css" rel="stylesheet">
</head>
<body>
<% Book book = (Book) request.getAttribute("book"); %>
<%
    AllServices allServices = (AllServices) pageContext.getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
    DescriptionShortener ds = allServices.getDescriptionShortener();
%>
<%--    <script>--%>
<%--        function myFunction (id) {--%>
<%--            location.href = "showBook?bookId=" + String(id);--%>
<%--        };--%>
<%--    </script>--%>

    <div class="elem">
        <img src=<%= book.getBook_photo()%> width="120" height="180">
        <br><br>
        <a href="showBook?bookId=<%= book.getBook_id()%>"><%= book.getBook_name() %></a>


    </div>
</body>
</html>
