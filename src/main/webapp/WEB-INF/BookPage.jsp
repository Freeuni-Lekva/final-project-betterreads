<%@ page import="Model.User" %>
<%@ page import="Constants.SharedConstants" %>
<%@ page import="Model.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Review" %>
<%@ page import="Service.*" %>


<html>
<head>
   <title>${bookName}</title>
    <link href="/Style/BookPageStyle.css" type="text/css" rel="stylesheet">
    <link href="/Style/GeneralStyle.css" type="text/css" rel="stylesheet">

</head>

<body>

<%
    HttpSession httpSession = pageContext.getSession();
    HttpServletRequest httpRequest = (HttpServletRequest) pageContext.getRequest();
    User admin = (User) httpSession.getAttribute(SharedConstants.ADMIN_SESSION);
    User user = (User) httpSession.getAttribute(SharedConstants.SESSION_ATTRIBUTE);
    Integer book_ID = (Integer)request.getAttribute("bookID");
    AllServices allServices = (AllServices) pageContext.getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
    UserBooksService ubs = allServices.getUserBooksService();
    ReviewService reviewService = allServices.getReviewService();
    UserService userService = allServices.getUserService();
    List<Review> reviews = reviewService.getReviewsByBookId(book_ID);

    if(admin == null){
%>

<jsp:include page='Header.jsp'>
    <jsp:param name="Header" value="Header"/>
</jsp:include>

<%} else {%>
<jsp:include page='AdminHeader.jsp'>
    <jsp:param name="AdminHeader" value="AdminHeader"/>
</jsp:include>
<%}%>

<br>

<div class="book">
    <div class="leftSide">
        <img src="${photo}" alt="${bookName}" width="300" height="450" class="img">
    </div>
    <div class="bookInfo">
        <h1>${bookName}</h1>
        <h3>By ${authorName}  (${year})</h3><br>
        <label>Rating - ${rating}</label><br>
        <label> Available - ${count}</label><br>
        <p>${description}</p>
    </div>
</div>


<form action="bookMarking?bookId=${bookID}" method="post">
    <input name="bookID" type="hidden" value="${bookID}"/>
    <%
        if(user != null && admin == null){
           if(!ubs.hasBookReserved(user.getUser_id(), book_ID )){
              if(allServices.getBookService().getBookCount(book_ID) > 0){%>
                <label><input type="submit" name="reserve" value="Reserve Book" class="button"></label>
            <%} else {%>
                <label class="notAvailable">Not available</label>
            <%}
            }
           else{%>
            <button type="button" class="reservedLabel">Reserved</button>
            <%
           }
            if(!ubs.hasBookForFuture(user.getUser_id(), book_ID)){%>
                <br>
                <label><input type="submit" name="mark" value="Mark As Interested" class="button"></label>
            <%}
            else{%>
                <br>
                <label><input type="submit" name="unmark" value="Remove Book" class="button"></label>
            <%}

    }
        boolean userReviewsOnly = (Boolean) httpRequest.getAttribute(SharedConstants.USER_REVIEWS);%>
</form>
<br>

<%if(user != null && admin == null){%>
    <div class="rate">
        <form action="/rating" method="post">
            <input name="book_id" type="hidden" value="${bookID}">
            <input type="radio" name="rating" value="1">
            <input type="radio" name="rating" value="2">
            <input type="radio" name="rating" value="3">
            <input type="radio" name="rating" value="4">
            <input type="radio" name="rating" value="5">
            <input type="submit" value="Submit">
        </form>
    </div>
<% }%>


<% if (user != null && admin == null) { %>
<form action="showReviews?bookId=${bookID}">
    <input name="bookID" type="hidden" value="${bookID}"/>
    <%if(user != null && admin == null){%>
    <form action = "reviewBook?bookId${bookID}" method="post">
        <input name="bookID" type="hidden" value="${bookID}"/>
        <textarea id="review" name="review" rows="4" cols="50"></textarea><br>
        <input type="submit" value = "Submit Review">
    </form>
    <%}%>
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

        if (userReviewsOnly) {
            reviews = reviewService.getReviewsByBookIdUserId(book_ID, user.getUser_id());
        }
        for(int i = 0; i < reviews.size(); i++){
            Review currReview = reviews.get(i); %>
            <p><%=userService.getUserById(currReview.getUser_id()).getUsername()%></p>
            <p><%=currReview.getDate().toString()%></p>
            <p>likes: <%=currReview.getNum_likes()%></p>
            <p><%=currReview.getComment()%></p><br>
            <% if(user != null && admin == null){
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
            }
        }%>

</form>

</body>
</html>
