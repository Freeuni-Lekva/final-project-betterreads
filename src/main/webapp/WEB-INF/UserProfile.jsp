<%@ page import="Model.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="Service.AllServices" %>
<%@ page import="Service.DescriptionShortener" %>
<%@ page import="Constants.SharedConstants" %>
<%@ page import="Service.BookService" %><%--
  Created by IntelliJ IDEA.
  User: etsir
  Date: 8/4/2021
  Time: 3:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
    <link href="/Style/SuggestionsStyle.css" type="text/css" rel="stylesheet">
    <link href="/Style/GeneralStyle.css" type="text/css" rel="stylesheet">
    <link href="/Style/UserProfileStyle.css" type="text/css" rel="stylesheet">
</head>
<body>

<%
    AllServices allServices = (AllServices) pageContext.getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
    BookService bookService = allServices.getBookService();
    DescriptionShortener ds = allServices.getDescriptionShortener();
%>

<jsp:include page='Header.jsp'>
    <jsp:param name="Header" value="Header"/>
</jsp:include>

<p>${name } ${lastName}</p>
<p>email: ${email}</p>
<p>username: ${username}</p>



<div class="grid-wrapper">
    <h4 id="title-1">Your reserved books: </h4>
    <h4 id="title-2">Books you have already read: </h4>
    <h4 id="title-3">Your books marked for future: </h4>

    <div id="list-1" class="wrapper">
        <ul class="demo">
            <%  List<Book> reserved = (List<Book>) request.getAttribute("reserved");
                for(Book b : reserved){
                    request.setAttribute("book", b);%>
            <li id="prev" class="preview" onclick="myFunction(<%= b.getBook_id()%>)">
                <%--                <div>--%>
                <jsp:include page='BookPreview.jsp'>
                    <jsp:param name="bok" value="${b}"/>
                </jsp:include>
                <%--                </div>--%>
                <span class="tooltiptext">
                            <%=b.getBook_name()%> <br>
                            (<%=b.getRelease_year()%>) <br>
                            author: <%=bookService.getAuthorById(b.getBook_id()).getAuthor_name()%> <br>
                            rating: <%= b.getBook_rating()%> <br>
                            description: <%=ds.shorten(b.getBook_description())%>
                        </span>
            </li>
            <%}%>
        </ul>
    </div>

    <div id="list-2" class="wrapper">
        <ul class="demo">
            <%  List<Book> read = (List<Book>) request.getAttribute("read");
                for(Book b : read){
                    request.setAttribute("book", b);%>
            <li id="prev" class="preview" onclick="myFunction(<%= b.getBook_id()%>)">
                <%--                <div>--%>
                <jsp:include page='BookPreview.jsp'>
                    <jsp:param name="bok" value="${b}"/>
                </jsp:include>
                <%--                </div>--%>
                <span class="tooltiptext">
                            <%=b.getBook_name()%> <br>
                            (<%=b.getRelease_year()%>) <br>
                            author: <%=bookService.getAuthorById(b.getBook_id()).getAuthor_name()%> <br>
                            rating: <%= b.getBook_rating()%> <br>
                            description: <%=ds.shorten(b.getBook_description())%>
                        </span>
            </li>
            <%}%>
        </ul>
    </div>



    <div id="list-3" class="wrapper">
        <ul class="demo"  class="wrapper">
            <%  List<Book> marked = (List<Book>) request.getAttribute("marked");
                for(Book b : marked){
                    request.setAttribute("book", b);%>
            <li id="prev" class="preview" onclick="myFunction(<%= b.getBook_id()%>)">
                <%--                <div>--%>
                <jsp:include page='BookPreview.jsp'>
                    <jsp:param name="bok" value="${b}"/>
                </jsp:include>
                <%--                </div>--%>
                <span class="tooltiptext">
                            <%=b.getBook_name()%> <br>
                            (<%=b.getRelease_year()%>) <br>
                            author: <%=bookService.getAuthorById(b.getBook_id()).getAuthor_name()%> <br>
                            rating: <%= b.getBook_rating()%> <br>
                            description: <%=ds.shorten(b.getBook_description())%>
                        </span>
            </li>
            <%}%>
        </ul>
    </div>

</div>


</body>
</html>
