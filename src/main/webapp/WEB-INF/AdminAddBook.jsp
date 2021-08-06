<html>

<head>
   <title>Add Book</title>
</head>

<body>
   <form action="/addBook" method = "post">

       <label for="bookName">Book Name</label>
       <input type="text" id="bookName" name="bookName" >
       <label for="authorName">Author</label>
       <input type="text" id="authorName" name="authorName" >
       <label for="year">Release Year</label>
       <input type="text" id="year" name="year" >
       <label for="description">Book Description</label>
       <input type="text" id="description" name="description" >
       <label for="count">Available Count</label>
       <input type="text" id="count" name="count" >

       <c:forEach items="${genres}" var="genre">
                   <input type="checkbox" id="${genre}" name="genres" value="${genre}">
                   <label for="${genre}"> ${genre} </label><br>
       </c:forEach>

       <button type="submit">Add Book</button>
   </form>

</body>
</html>