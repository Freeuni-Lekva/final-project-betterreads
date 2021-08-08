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
    <link href="/Style/Header.css" type="text/css" rel="stylesheet">

</head>
<body>

<%
    HttpSession httpSession = pageContext.getSession();
    User user = (User) httpSession.getAttribute(SharedConstants.SESSION_ATTRIBUTE);
%>
<div class="topNav">
    <div class="upper">
        <form action="/home" method = "post" class="item">
            <button type="submit" class="btn-link"> BetterReads </button>
        </form>

<%--        <form id="catalogue" action="/catalogue" method="post">--%>
<%--            <a href="javascript:;" onclick="document.getElementById('catalogue').submit();" class="item">Catalogue</a>--%>
<%--        </form>--%>

        <form action="/catalogue" method = "get" class="item">
            <button type="submit" class="btn-link"> Catalogue </button>
        </form>

        <%if(user == null){%>
            <a href="/login" class="item">Sign In</a>
            <a href="/register" class="item">Register</a>
        <%} else {%>
            <form action="/suggestions" method = "post" class="item">
                <button type="submit" class="btn-link">Suggest me a book</button>
            </form>


            <a href="userProfile?userId=<%= user.getUser_id()%>" class="item">Your Profile</a>

            <form action="/logout" method = "post" class="item">
                <button type="submit" class="btn-link">Log Out</button>
            </form>
        <%}%>
    </div>
    <div class="lower">
        <form action="/search" method = "post">
            <input type="text" id="stext" name="stext" placeholder="Search books/authors...">
            <button type="submit">search</button>
        </form>
    </div>
</div>
</body>
</html>
