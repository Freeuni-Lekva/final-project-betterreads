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

<form action="/catalogue" method = "post">
    <input type="submit" name="action" value="Get available books">
</form>


<fieldset> <legend>Choose your interests</legend>
    <form action="/catalogue" method = "post">
        <input type="checkbox" id="genre1" name="genres" value="Novel">
        <label for="genre1"> Novel </label><br>
        <input type="checkbox" id="genre2" name="genres" value="Tragedy">
        <label for="genre2"> Tragedy </label><br>
        <input type="checkbox" id="genre3" name="genres" value="Romance">
        <label for="genre3"> Romance </label><br>
        <input type="checkbox" id="genre4" name="genres" value="Detective">
        <label for="genre4"> Detective </label><br>
        <input type="checkbox" id="genre5" name="genres" value="Adventure">
        <label for="genre5"> Adventure </label><br>
        <input type="checkbox" id="genre6" name="genres" value="Historical">
        <label for="genre6"> Historical</label><br>
        <input type="checkbox" id="genre7" name="genres" value="Dystopian">
        <label for="genre7"> Dystopian </label><br>
        <input type="checkbox" id="genre8" name="genres" value="Satire">
        <label for="genre8"> Satire </label><br>
        <input type="checkbox" id="genre9" name="genres" value="Mystery">
        <label for="genre9"> Mystery </label><br>
        <input type="checkbox" id="genre10" name="genres" value="Bildungsroman">
        <label for="genre10"> Bildungsroman </label><br>
        <input type="submit" name="GenreFilter" value="GenreFilter">
    </form>
</fieldset>


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
