<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
   <title>Add Genre</title>
</head>

<body>

   <jsp:include page='AdminHeader.jsp'>
          <jsp:param name="AdminHeader" value="AdminHeader"/>
   </jsp:include>

   <form action="/adminGenres" method = "post">
       <label for="genreName">Genre</label>
       <input type="text" id="genreName" name="genreName" >
       <button type="submit">Add Genre</button>
   </form>

</body>
</html>