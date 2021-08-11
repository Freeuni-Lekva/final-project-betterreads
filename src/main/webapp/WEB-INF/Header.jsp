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

<script>
    function toHome () {
        location.href = "home";
    };
    function toCatalogue () {
        location.href = "catalogue";
    };
</script>

<div class="topNav">
    <div class="upper">
        <form action="/home" method = "post" class="item" onclick="toHome()">
            <button type="submit" class="btn-link"> BetterReads </button>
        </form>

        <form action="/catalogue" method = "get" class="item" onclick="toCatalogue()">
            <button type="submit" class="btn-link"> Catalogue </button>
        </form>

        <div class="search-container">
            <form action="/search" method="post">
                <input type="text" placeholder="Search books/authors..." name="stext">
<%--                <button type="submit"><i class="fa fa-search"></i></button>--%>
            </form>
        </div>


        <div class="user">
            <%if(user == null){%>
            <a href="/login" class="item" id="sign_in">Sign In</a>
            <a href="/register" class="item" id="register">Register</a>
            <%} else {%>
            <form action="/suggestions" method = "post" class="item">
                <button type="submit" class="btn-link">Suggest me a book</button>
            </form>

            <a href="userProfile?userId=<%= user.getUser_id()%>" class="item" id="profile">Your Profile</a>

            <form action="/logout" method = "post" class="item" id="logout">
                <button type="submit" class="btn-link">Log Out</button>
            </form>
            <%}%>

        </div>
    </div>
<%--    <div class="lower">--%>
<%--    </div>--%>
</div>
</body>
</html>
