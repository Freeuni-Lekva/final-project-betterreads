<%@ page import="Constants.SharedConstants" %>
<%@ page import="Model.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="Service.AllServices" %>
<%@ page import="Model.User" %>
<%@ page import="Service.BookService" %><%--
  Created by IntelliJ IDEA.
  User: etsir
  Date: 8/3/2021
  Time: 6:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Better Reads</title>
</head>
<body>
    <h1>Welcome to Better Reads!</h1>

    <jsp:include page='Header.jsp'>
        <jsp:param name="Header" value="Header"/>
    </jsp:include>


    <form action="/catalogue" method = "get">
        <button type="submit"> Catalogue </button>
    </form>
 <br>
    <%
        AllServices allServices = (AllServices) pageContext.getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        BookService bb = allServices.getBookService();
        List<Book> list = bb.getBestBooks(1, 5);
    %>
    <ul>
    <%
        for(Book b : list){
            request.setAttribute("book", b);
    %>
            <jsp:include page='BookPreview.jsp'>
                <jsp:param name="book" value="${b}"/>
            </jsp:include>
    <%
        }
    %>
    </ul>

</body>
</html>
