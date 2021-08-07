<%@ page import="Model.User" %>
<%@ page import="Constants.SharedConstants" %>
<%@ page import="Model.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Review" %>
<%@ page import="Service.*" %>


<html>
<head>
   <title>${bookName}</title>

    <link rel="stylesheet" href="jquery.rating.css">
    <script src="jquery.js"></script>
    <script src="jquery.rating.js"></script>
</head>

<body>

<%
    HttpSession httpSession = pageContext.getSession();
    User admin = (User) httpSession.getAttribute(SharedConstants.ADMIN_SESSION);
    if(admin == null){
%>

<jsp:include page='Header.jsp'>
    <jsp:param name="Header" value="Header"/>
</jsp:include>

<%
} else {
%>
<jsp:include page='AdminHeader.jsp'>
    <jsp:param name="AdminHeader" value="AdminHeader"/>
</jsp:include>
<%
    }
%>

<br>

<h1>${bookName}</h1>
<form action="bookMarking?bookId=${bookID}" method="post">
    <input name="bookID" type="hidden" value="${bookID}"/>
    <label>Author - ${authorName}</label>
    <label>Rating - ${rating}</label>
    <label>Release year - ${year}</label>
    <label>Available - ${count}</label><br>
    <img src="${photo}" alt="${bookName}" width="200" height="300">
    <p>${description}</p>

    <%
        httpSession = pageContext.getSession();
        User user = (User) httpSession.getAttribute(SharedConstants.SESSION_ATTRIBUTE);
        Integer book_ID = (Integer)request.getAttribute("bookID");
        if(user != null && admin == null){
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
</form><br>
    <%
        AllServices allServices = (AllServices) pageContext.getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        ReviewService reviewService = allServices.getReviewService();
        UserService userService = allServices.getUserService();
        List<Review> allReviews = reviewService.getReviewsByBookId(book_ID);
        for(int i = 0; i < allReviews.size(); i++){
            Review currReview = allReviews.get(i); %>
            <p><%=userService.getUserById(currReview.getUser_id()).getUsername()%></p>
            <p><%=currReview.getDate().toString()%></p>
            <p>likes: <%=currReview.getNum_likes()%></p>
            <p><%=currReview.getComment()%></p><br>
<%
        }
        if(user != null && admin == null){
            %>
            <form action = "reviewBook?bookId${bookID}" method="post">
                <input name="bookID" type="hidden" value="${bookID}"/>
                <textarea id="review" name="review" rows="4" cols="50"></textarea><br>
                <input type="submit" value = "Submit Review">
            </form>

<%
        }
    %>
</form>

<%
    if(user != null && admin == null){
%>
<form action="/rating" method="post">
    <input name="book_id" type="hidden" value="${bookID}">
    <input type="radio" name="rating" value="1">
    <input type="radio" name="rating" value="2">
    <input type="radio" name="rating" value="3">
    <input type="radio" name="rating" value="4">
    <input type="radio" name="rating" value="5">
    <input type="submit" value="Submit">
</form>
<%
    }
%>
</body>
</html>
