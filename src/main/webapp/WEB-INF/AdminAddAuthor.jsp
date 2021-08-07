<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
   <title>Add Author</title>
</head>

<body>
   <jsp:include page='AdminHeader.jsp'>
          <jsp:param name="AdminHeader" value="AdminHeader"/>
   </jsp:include>

   <form action="/adminAuthors" method = "post">
       <label for="authorName">Author Name</label>
       <input type="text" id="authorName" name="authorName" >
       <button type="submit">Add Author</button>
   </form>

</body>
</html>