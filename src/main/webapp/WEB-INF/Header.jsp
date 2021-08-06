<%@ page import="Model.User" %>
<%@ page import="Constants.SharedConstants" %><%--
  Created by IntelliJ IDEA.
  User: GG
  Date: 8/6/2021
  Time: 11:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>

<form action="/search" method = "post">
    <label for="stext">Search books:</label>
    <input type="text" id="stext" name="stext" >
    <button type="submit">search</button>
</form>

<form action="/home" method = "post">
    <button type="submit"> BetterReads </button>
</form>

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
<br><br>

</body>
</html>
