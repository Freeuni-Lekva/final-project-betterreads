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

<jsp:include page='Header.jsp'>
    <jsp:param name="Header" value="Header"/>
</jsp:include>


<%
    AllServices allServices = (AllServices) pageContext.getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
    BookService bs = allServices.getBookService();
    List<Book> list = (List<Book>) request.getAttribute("list");
    if(list == null){
        list = bs.getAllBooks();
    }
%>


<form action="/catalogue" method= "post">
    <label for="SortBooks">Sort books:</label>
    <select id="SortBooks" name="SortBooks">
        <option value="High -> Low">Rating: High -> Low</option>
        <option value="Low -> High">Rating: Low -> High</option>
        <option value="old to new">Release year: Old -> New</option>
        <option value="new to old">Release year: New -> Old</option>
    </select><br>

    <input type="checkbox" id="Available" name="Available" value="Available">
    <label for="Available"> Get available books </label><br>

    <input type="submit" Submit ="Sort"/>
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
