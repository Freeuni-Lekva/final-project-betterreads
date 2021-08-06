<%@ page import="Model.User" %>
<%@ page import="Constants.SharedConstants" %>
<%@ page import="Model.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="Service.AllServices" %>
<%@ page import="Service.BookService" %>
<%@ page import="Service.UserBooksService" %>


<html>
<head>
   <title>${bookName}</title>

    <link rel="stylesheet" href="jquery.rating.css">
    <script src="jquery.js"></script>
    <script src="jquery.rating.js"></script>
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
<form action="bookMarking?bookId=${bookID}" method="post">
    <input name="bookID" type="hidden" value="${bookID}"/>
    <label>Author - ${authorId}</label>
    <label>Rating - ${rating}</label>
    <label>Release year - ${year}</label>
    <label>Available - ${count}</label>
    <p>${description}</p>

    <%
        HttpSession httpSession = pageContext.getSession();
        User user = (User) httpSession.getAttribute(SharedConstants.SESSION_ATTRIBUTE);
        Integer book_ID = (Integer)request.getAttribute("bookID");
        if(user != null){
           AllServices allServices = (AllServices) pageContext.getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
           UserBooksService ubs = allServices.getUserBooksService();
           if(!ubs.hasBookReserved(user.getUser_id(), book_ID )){

    %>
    <label><input type="submit" name="reserve" value="Reserve Book"></label>
    <%
           }
           if(!ubs.hasBookForFuture(user.getUser_id(), book_ID)){
    %>
    <label><input type="submit" name="mark" value="Mark As Interested"></label>
    <%
           } else {
    %>
    <label><input type="submit" name="unmark" value="Remove Book"></label>
    <%
           }
       }
    %>

    <form action="/rating" method="post">
        <input type="radio" name="rating" value="1" class="star">
        <input type="radio" name="rating" value="2" class="star">
        <input type="radio" name="rating" value="3" class="star">
        <input type="radio" name="rating" value="4" class="star">
        <input type="radio" name="rating" value="5" class="star">
        <input type="submit" name="rate" value="rate">
    </form>

</form>
</body>
</html>
