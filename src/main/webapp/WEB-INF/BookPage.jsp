<%@ page import="Model.User" %>
<%@ page import="Constants.SharedConstants" %>

<html>
<head>
   <title>${bookName}</title>
</head>

<body>

<jsp:include page='Header.jsp'>
    <jsp:param name="Header" value="Header"/>
</jsp:include>

<form action="/catalogue" method = "get">
    <button type="submit"> Catalogue </button>
</form>
<br>

<h1>${bookName}</h1>
<form action="/bookMarking" method="post">
    <input name="bookID" type="hidden" value="${bookID}"/>
    <label>Author - ${authorId}</label>
    <label>Rating - ${rating}</label>
    <label>Release year - ${year}</label>
    <label>Available - ${count}</label>
    <p>${description}</p>


</form>
</body>
</html>
