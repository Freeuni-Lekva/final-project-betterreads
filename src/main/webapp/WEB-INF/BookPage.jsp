<%@ page import="Model.User" %>
<%@ page import="Constants.SharedConstants" %>
<%@ page import="Model.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Review" %>
<%@ page import="Service.*" %>


<html>
<head>
   <title>${bookName}</title>
</head>

<body>

<%
    HttpSession httpSession = pageContext.getSession();
    HttpServletRequest httpRequest = (HttpServletRequest) pageContext.getRequest();
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
    <label>Author - ${authorId}</label>
    <label>Rating - ${rating}</label>
    <label>Release year - ${year}</label>
    <label>Available - ${count}</label><br>
    <img src="${photo}" alt="${bookName}" width="200" height="300">
    <p>${description}</p>

    <%
        httpSession = pageContext.getSession();
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
           if(!ubs.hasBookForFuture(user.getUser_id(), book_ID) && !ubs.hasReadBook(user.getUser_id(), book_ID)){
    %>
    <label><input type="submit" name="mark" value="Mark As Interested"></label>
    <%
           }
           if(ubs.hasBookForFuture(user.getUser_id(), book_ID)) {
    %>
    <label><input type="submit" name="unmark" value="Remove Book"></label>
    <%
           }
        }
        boolean userReviewsOnly = (Boolean) httpRequest.getAttribute(SharedConstants.USER_REVIEWS);
    %>
</form><br>

<% if (user != null) { %>
<form action="showReviews?bookId=${bookID}">
    <input name="bookID" type="hidden" value="${bookID}"/>
    <% if(!userReviewsOnly){ %>
    <label><input type="submit" name="user_reviews" value="Show only my reviews"></label>
    <% } else { %>
    <label><input type="submit" name="all_reviews" value="Show all reviews"></label>
    <%
        }
    }
    %>
</form>


    <%
        AllServices allServices = (AllServices) pageContext.getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        ReviewService reviewService = allServices.getReviewService();
        UserService userService = allServices.getUserService();
        List<Review> reviews = reviewService.getReviewsByBookId(book_ID);

        if (userReviewsOnly) {
            reviews = reviewService.getReviewsByBookIdUserId(book_ID, user.getUser_id());
        }
        for(int i = 0; i < reviews.size(); i++){
            Review currReview = reviews.get(i); %>
            <p><%=userService.getUserById(currReview.getUser_id()).getUsername()%></p>
            <p><%=currReview.getDate().toString()%></p>
            <p>likes: <%=currReview.getNum_likes()%></p>
            <p><%=currReview.getComment()%></p><br>
            <% if(user != null){
                if(reviewService.hasUserLikedAlready(currReview.getReview_id(), user.getUser_id())){
            %>
                    <form action="likeReview?reviewId=<%=currReview.getReview_id()%>" method="post">
                        <input name="bookID" type="hidden" value="${bookID}"/>
                        <input name="reviewId" type="hidden" value="<%=currReview.getReview_id()%>"/>
                        <input name="all_reviews" type="hidden" value ="<%=!userReviewsOnly%>">
                        <label><input type="submit" name="unlike_review" value="Unlike"></label>
                    </form>

            <%
                } else {
                    %>
                    <form action="likeReview?reviewId=<%=currReview.getReview_id()%>" method="post">
                        <input name="bookID" type="hidden" value="${bookID}"/>
                        <input name="reviewId" type="hidden" value="<%=currReview.getReview_id()%>"/>
                        <input name="all_reviews" type="hidden" value ="<%=!userReviewsOnly%>">
                        <label><input type="submit" name="like_review" value="Like"></label>
                    </form>
                <%}
                if(user.getUser_id() == currReview.getUser_id()){ %>
                    <form action="deleteReview?reviewId=<%=currReview.getReview_id()%>" method="post">
                        <input name="bookID" type="hidden" value="${bookID}"/>
                        <input name="reviewId" type="hidden" value="<%=currReview.getReview_id()%>"/>
                        <input name="all_reviews" type="hidden" value ="<%=!userReviewsOnly%>">
                        <label><input type="submit" name="delete_review" value="Delete Review"></label>
                    </form>
            <%  }
            } %>
<%
        }
        if(user != null){
            %>
            <form action = "reviewBook?bookId${bookID}" method="post">
                <input name="bookID" type="hidden" value="${bookID}"/>
                <textarea id="review" name="review" rows="4" cols="50"></textarea><br>
                <input type="submit" value = "Submit Review">
            </form>

<%
        }
    %>

</body>
</html>
