<%@ page import="java.util.List" %>
<%@ page import="Model.Book" %>
<%@ page import="Service.AllServices" %>
<%@ page import="Service.BookService" %>
<%@ page import="Constants.SharedConstants" %>
<%@ page import="Service.DescriptionShortener" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Vano
  Date: 06.08.2021
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Suggestions</title>
    <link href="/Style/SuggestionsStyle.css" type="text/css" rel="stylesheet">
    <link href="/Style/GeneralStyle.css" type="text/css" rel="stylesheet">
</head>
<body>

<jsp:include page='Header.jsp'>
    <jsp:param name="Header" value="Header"/>
</jsp:include>

<%
    AllServices allServices = (AllServices) pageContext.getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
    BookService bookService = allServices.getBookService();
    DescriptionShortener ds = allServices.getDescriptionShortener();
    List<Book> list =(List<Book>) request.getAttribute("list");
%>

<h1>
    Here are suggestions for you:
</h1>
<br>

<div class="wrapper">
    <ol class="demo">
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
    </ol>
</div>

</body>
</html>
