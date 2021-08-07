<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
   <title>Add Book</title>
</head>

<body>

<jsp:include page='AdminHeader.jsp'>
    <jsp:param name="AdminHeader" value="AdminHeader"/>
</jsp:include>

<h2> Add Book</h2>

<form action="addBook" method="post">
    <table style="width: 25%">
        <tr>
            <td>Book Name: </td>
            <td><input type="text" name="book_name" /></td>
        </tr>
        <tr>
            <td>Author Name:</td>
            <td><input type="text" name="author_name" /></td>
        </tr>
        <tr>
            <td>Release Year:</td>
            <td><input type="text" name="release_year" /></td>
        </tr>
        <tr>
            <td>Count Available:</td>
            <td><input type="text" name="count" /></td>
        </tr>

        <tr>
            <td>Link for image:</td>
            <td><input type="text" name="photo" /></td>
        </tr>

    </table>
    <h4> Choose genres: </h4>

    <c:forEach items="${genres}" var="genre">
        <input type="checkbox" id="${genre}" name="genre" value="${genre}">
        <label for="${genre}"> ${genre} </label><br>
    </c:forEach>

    <label for="book_description">Book Description: </label>
    <textarea id="book_description" name="book_description" rows="4" cols="50"></textarea>

    <br>
    <input type="submit" value="Add Book" />
</form>

</body>
</html>