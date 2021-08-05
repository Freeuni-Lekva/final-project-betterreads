<%@ page import="Service.AllServices" %>
<%@ page import="Model.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="Constants.SharedConstants" %>
<%@ page import="Service.BookService" %>
<%@ page import="java.util.Collections" %><%--
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
</head>
<body>

<%
    AllServices allServices = (AllServices) pageContext.getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
    BookService bs = allServices.getBookService();
    List<Book> list = (List<Book>) request.getAttribute("list");
    if(list == null){
        list = bs.getAllBooks();
    }
%>

<form action="/catalogue" method= "post">
    <label for="booksYear">Sort books:</label>
    <select id="booksYear" name="booksYear">
        <option value="old to new">Old -> New</option>
        <option value="new to old">New -> Old</option>
    </select>
    <input type="submit" value="Sort by year"/>
</form>

<form action="/catalogue" method= "post">
    <label for="bookRating">Sort books:</label>
    <select id="bookRating" name="bookRating">
        <option value="High -> Low">High -> Low</option>
        <option value="Low -> High">Low -> High</option>
    </select>
    <input type="submit" value="Sort by rating"/>
</form>

<ul>
    <%
        for(Book b : list){
            String url = b.getBook_photo();
            request.setAttribute("book", b);
    %>
    <jsp:include page='BookPreview.jsp'>
        <jsp:param name="bok" value="${b}"/>
    </jsp:include>
    <%
        }
    %>
</ul>

</body>
</html>
