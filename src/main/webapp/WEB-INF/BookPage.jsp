<%@ page import="Model.User" %>
<%@ page import="Constants.SharedConstants" %>

<html>
<head>
   <title>${bookName}</title>
</head>

<body>

<h1>${bookName}</h1>
<form action="/bookMarking" method="post">
    <input name="bookID" type="hidden" value="${bookID}"/>
    <label>Author - ${authorId}</label>
    <label>Rating - ${rating}</label>
    <label>Release year - ${year}</label>
    <label>Available - ${count}</label>
    <p>${description}</p>

    <%
        HttpSession httpSession = pageContext.getSession();
        User user = (User) httpSession.getAttribute(SharedConstants.SESSION_ATTRIBUTE);

        if(user != null){
    %>
    <label><input type="submit" name="reserve" value="Reserve Book"></label>
    <label><input type="submit" name="mark" value="Mark As Interested"></label>
    <%
       } else {
    %>
    <a href="/login">Already a member? Sign In</a>
    <br>
    <a href="/register">Register here!</a>
    <%
       }
    %>

</form>
</body>
</html>
