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

<body>
<%
    HttpSession httpSession = pageContext.getSession();
    User admin = (User) httpSession.getAttribute(SharedConstants.ADMIN_SESSION);
    if(admin != null){
%>
<form action="/search" method = "post">
    <label for="stext">Search books:</label>
    <input type="text" id="stext" name="stext" >
    <button type="submit">search</button>
</form>

<form action="/logoutAdmin" method = "post">
    <button type="submit">Log Out</button>
</form>

<form action="/adminHome" method = "post">
    <button type="submit"> BetterReadsAdmin </button>
</form>

<form action="/catalogue" method = "get">
    <button type="submit"> Catalogue </button>
</form>

<%
    }
%>

</body>
</html>
