<%@ page import="Model.Book" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: etsir
  Date: 8/4/2021
  Time: 3:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
</head>
<body>

<form action="/home" method = "post">
    <button type="submit"> BetterReads </button>
</form>

<p>${name } ${lastName}</p>
<p>email: ${email}</p>
<p>username: ${username}</p>
<h4>Your reserved books: </h4>
<%
    List<Book> reserved = (List<Book>) request.getAttribute("reserved");
    for(Book book : reserved){
%>
    <li><%=book.getBook_name()%></li>
<%
    }
%>
<h4>Books you have already read: </h4>
<%
    List<Book> read = (List<Book>) request.getAttribute("read");
    for(Book book : read){
%>
<li><%=book.getBook_name()%></li>
<%
    }
%>
<h4>Your books marked for future: </h4>
<%
    List<Book> marked = (List<Book>) request.getAttribute("marked");
    for(Book book : marked){
%>
<li><%=book.getBook_name()%></li>
<%
    }
%>
<form action="/home" method = "post">
    <button type="submit">Home Page</button>
</form>
</body>
</html>
