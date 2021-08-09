<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="Service.AllServices" %>
<%@ page import="Model.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="Constants.SharedConstants" %>
<%@ page import="Service.BookService" %>
<%@ page import="java.util.Collections" %>
<%@ page import="Model.User" %>
<%@ page import="Service.DescriptionShortener" %>
<%@ page import="Dao.AuthorDao" %><%--
  Created by IntelliJ IDEA.
  User: GG
  Date: 8/5/2021
  Time: 12:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Catalogue</title>
    <link href="/Style/CatalogueStyle.css" type="text/css" rel="stylesheet">
    <link href="/Style/GeneralStyle.css" type="text/css" rel="stylesheet">
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

<%
    AllServices allServices = (AllServices) pageContext.getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
    BookService bs = allServices.getBookService();
    List<Book> list = (List<Book>) request.getAttribute("books");
    if(list == null){
        list = bs.getAllBooks();
    }
%>

<div class="wrapper">
    <div class="filters">
        <fieldset>
            <legend>Choose your interests</legend>
            <form action="/catalogue" method= "post">
                <label for="SortBooks">Sort books:</label>
                <select id="SortBooks" name="SortBooks">
                    <option value="High -> Low">Rating: High -> Low</option>
                    <option value="Low -> High">Rating: Low -> High</option>
                    <option value="old to new">Release year: Old -> New</option>
                    <option value="new to old">Release year: New -> Old</option>
                </select><br><br>
                <input type="checkbox" id="Available" name="Available" value="Available">
                <label for="Available"> Get available books </label><br>
                <h2> Choose genres: </h2>

                <c:forEach items="${genres}" var="genre">
                    <input type="checkbox" id="${genre}" name="genre" value="${genre}">
                    <label for="${genre}"> ${genre} </label><br>
                </c:forEach>

                <input type="submit" Submit ="Sort"/>
            </form>
        </fieldset>
    </div>

    <script>
        function myFunction (id) {
            location.href = "showBook?bookId=" + String(id);
        };
    </script>

    <%
//        AllServices allServices = (AllServices) pageContext.getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        DescriptionShortener ds = allServices.getDescriptionShortener();
        BookService bookService = allServices.getBookService();
    %>

    <ul class="demo">
        <%for(Book b : list){
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
</body>
</html>
