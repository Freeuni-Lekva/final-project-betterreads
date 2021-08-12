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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>

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
<% boolean userReviewsOnly = (Boolean) httpRequest.getAttribute(SharedConstants.USER_REVIEWS);%>


<br>

<div class="book">
    <div class="leftSide">
        <img src="${photo}" alt="${bookName}" width="300" height="450" class="img">
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
            <label><input type="submit" name="unmark" value="Remove as interested" class="button"></label>
            <%}

            }%>
        </form>
        <%if(user != null && admin == null){%>
        <div class="container">
            <label>Rate this book:</label>
            <div class="star-widget">
                <input id="book" name="book_id" type="hidden" value="${bookID}">
                <input type="radio" name="rate" id="rate-5" ${rating == 5 ? "checked" : ""}>
                <label for="rate-5" class="fas fa-star" onclick="rate(5)"></label>
                <input type="radio" name="rate" id="rate-4" ${rating == 4 ? "checked" : ""}>
                <label for="rate-4" class="fas fa-star" onclick="rate(4)"></label>
                <input type="radio" name="rate" id="rate-3" ${rating == 3 ? "checked" : ""}>
                <label for="rate-3" class="fas fa-star" onclick="rate(3)"></label>
                <input type="radio" name="rate" id="rate-2" ${rating == 2 ? "checked" : ""}>
                <label for="rate-2" class="fas fa-star" onclick="rate(2)"></label>
                <input type="radio" name="rate" id="rate-1" ${rating == 1 ? "checked" : ""}>
                <label for="rate-1" class="fas fa-star" onclick="rate(1)"></label>
            </div>
        </div>
        <% }%>
    </div>

   <div class="rightSide">
        <div class="bookInfo">
            <h1>${bookName}</h1>
            <h3>By ${authorName}  (${year})</h3><br>
            <label>Rating - ${rating}</label><br>
            <label> Available - ${count}</label><br>
            <p>${description}</p>
        </div>

        <div class="review_panel">
            <%if(user != null && admin == null){%>
            <form action = "reviewBook?bookId${bookID}" method="post">
                <input name="bookID" type="hidden" value="${bookID}"/>
                <textarea id="review" name="review" rows="5" cols="60" placeholder="Write review here..."></textarea><br>
                <input type="submit" value = "Submit Review">
            </form>
            <%}%>


            <% if (user != null && admin == null) { %>
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

            <%if (userReviewsOnly) {
                reviews = reviewService.getReviewsByBookIdUserId(book_ID, user.getUser_id());
            }
                for(int i = 0; i < reviews.size(); i++){
                    Review currReview = reviews.get(i); %>
            <div class="review">
                <div class="review-heading">
                    <div class="heading_labels">
                        <h1><%=userService.getUserById(currReview.getUser_id()).getUsername()%></h1>
                        <h2>
                            <label><%=currReview.getNum_likes()%> likes</label> |
                            <label><%=currReview.getDate().toString()%></label>
                        </h2>
                    </div>
                    <div class="heading_buttons">
                        <% if(user != null && admin == null){
                            if(reviewService.hasUserLikedAlready(currReview.getReview_id(), user.getUser_id())){%>
                        <form action="likeReview?reviewId=<%=currReview.getReview_id()%>" method="post">
                            <input name="bookID" type="hidden" value="${bookID}"/>
                            <input name="reviewId" type="hidden" value="<%=currReview.getReview_id()%>"/>
                            <input name="all_reviews" type="hidden" value ="<%=!userReviewsOnly%>">
                            <label><input id="unlike" type="submit" name="unlike_review" value="Unlike"></label>
                        </form>
                        <%} else {%>
                        <form action="likeReview?reviewId=<%=currReview.getReview_id()%>" method="post">
                            <input name="bookID" type="hidden" value="${bookID}"/>
                            <input name="reviewId" type="hidden" value="<%=currReview.getReview_id()%>"/>
                            <input name="all_reviews" type="hidden" value ="<%=!userReviewsOnly%>">
                            <label><input id="like" type="submit" name="like_review" value="Like"></label>
                        </form>
                        <%}
                            if(user.getUser_id() == currReview.getUser_id()){ %>
                        <form action="deleteReview?reviewId=<%=currReview.getReview_id()%>" method="post">
                            <input name="bookID" type="hidden" value="${bookID}"/>
                            <input name="reviewId" type="hidden" value="<%=currReview.getReview_id()%>"/>
                            <input name="all_reviews" type="hidden" value ="<%=!userReviewsOnly%>">
                            <label><input id="delete" type="submit" name="delete_review" value="Delete Review"></label>
                        </form>
                        <%}
                        }%>
                    </div>
                </div>
                <div class="comment">
                    <p><%=currReview.getComment()%></p><br>
                </div>
            </div>
            <%}%>
        </div>
    </div>
</div>


<br>


<script>
    function rate(rating){
        const bookId = document.getElementById("book").getAttribute("value");
        let form = document.createElement('form');
        form.setAttribute('method', 'post');
        form.setAttribute('action', '/rating');
        form.style.display = 'hidden';
        let input = document.createElement("input");
        input.setAttribute("type", "hidden");
        input.setAttribute("name", "book_id");
        input.setAttribute("value", bookId);
        form.appendChild(input);
        let rate = document.createElement("input");
        rate.setAttribute("type", "hidden");
        rate.setAttribute("name", "rating");
        rate.setAttribute("value", rating);
        form.appendChild(rate);
        document.body.appendChild(form)
        form.submit();
    }
</script>


</body>
</html>
