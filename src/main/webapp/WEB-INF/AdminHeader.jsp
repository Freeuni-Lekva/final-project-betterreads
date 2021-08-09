<%@ page import="Constants.SharedConstants" %>
<%@ page import="Model.User" %><%--
  Created by IntelliJ IDEA.
  User: etsir
  Date: 8/6/2021
  Time: 9:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="/Style/Header.css" type="text/css" rel="stylesheet">
    <link href="/Style/SearchBarStyle.css" type="text/css" rel="stylesheet">

</head>
<body>
<script>
    function toHome () {
        location.href = "adminHome";
    };
    function toCatalogue () {
        location.href = "catalogue";
    };
</script>
<%
    HttpSession httpSession = pageContext.getSession();
    User admin = (User) httpSession.getAttribute(SharedConstants.ADMIN_SESSION);
    if(admin != null){
%>

<div class="topNav">
    <div class="upper">
        <form action="/adminHome" method = "post" class="item" onclick="toHome()">
            <button type="submit" class="btn-link"> BetterReadsAdmin </button>
        </form>

        <form action="/catalogue" method = "get" class="item" onclick="toCatalogue()">
            <button type="submit" class="btn-link"> Catalogue </button>
        </form>

        <form action="/logoutAdmin" method = "post" class="item">
            <button type="submit" class="btn-link">Log Out</button>
        </form>
    </div>
    <div class="lower">
        <form action="/search" method = "post">
            <input type="text" id="stext" name="stext" placeholder="Search books/authors...">
            <button type="submit">search</button>
        </form>
    </div>
</div>

<%
    }
%>

</body>
</html>
