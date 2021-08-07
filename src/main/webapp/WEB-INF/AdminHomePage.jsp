<%@ page import="Model.User" %>
<%@ page import="Constants.SharedConstants" %><%--
  Created by IntelliJ IDEA.
  User: etsir
  Date: 8/6/2021
  Time: 5:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Page</title>
</head>
<body>

<jsp:include page='AdminHeader.jsp'>
    <jsp:param name="AdminHeader" value="AdminHeader"/>
</jsp:include>

<%
    HttpSession httpSession = pageContext.getSession();
    User admin = (User) httpSession.getAttribute(SharedConstants.ADMIN_SESSION);
    if(admin == null){
%>
<h2>You have no access here!</h2>
<%
        } else {
%>
<h2>Here are admin functions:</h2>
<br>
<h4><a href="/adminReservations">View all reservations from here</a></h4>
<br>
<h4><a href="/adminAuthors">Add author from here</a></h4>
<br>
<h4><a href="/adminGenres">Add genre from here</a></h4>
<br>
<h4><a href="/addBook">Add new book </a></h4>
<%
    }
%>
</body>
</html>
