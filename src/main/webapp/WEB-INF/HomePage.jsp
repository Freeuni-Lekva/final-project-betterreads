<%@ page import="Constants.SharedConstants" %>
<%@ page import="Service.bestBooks" %>
<%@ page import="Model.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="Service.AllServices" %>
<%@ page import="Model.User" %><%--
  Created by IntelliJ IDEA.
  User: etsir
  Date: 8/3/2021
  Time: 6:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Better Reads</title>
</head>
<body>
    <h1>Welcome to Better Reads!</h1>
<%
    HttpSession httpSession = pageContext.getSession();
    User user = (User) httpSession.getAttribute(SharedConstants.SESSION_ATTRIBUTE);

    if(user == null){
%>

    <a href="/login">Already a member? Sign In</a>
<br>
    <a href="/register">Register here!</a>
    <%
        } else {

    %>
    <a href="userProfile?userId=<%= user.getUser_id()%>"><p>visit your profile</p>
    </a>
    <form action="/logout" method = "post">
        <button type="submit">Log Out</button>
    </form>
    <%
        }
    %>
<br>
    <form action="/search" method = "post">
        <label for="stext">Search books:</label>
        <input type="text" id="stext" name="stext" >
        <button type="submit">search</button>
    </form>
<br>
    <%
        AllServices allServices = (AllServices) pageContext.getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        bestBooks bb = allServices.getBestBooks();
        List<Book> list = bb.getBestBooks(1, 5);
    %>
    <ul>
    <%
        for(Book b : list){
    %>
    <li><%= b.getBook_name() %></li>
    <%
        }
    %>
    </ul>

</body>
</html>
