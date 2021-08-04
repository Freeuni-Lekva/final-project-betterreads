<html>
<head>
   <title>${bookName}</title>
</head>

<body>

<h1>${bookName}</h1>
<form action="bookMarking" method="post">
    <input name="bookID" type="hidden" value="${bookID}"/>
    <label>Author - ${authorId}</label>
    <label>Rating - ${rating}</label>
    <label>Release year - ${year}</label>
    <label>Available - ${count}</label>
    <p>${description}</p>
    <label><input type="submit" value="Reserve Book"></label>
    <label><input type="submit" value="Mark As Interested"></label>
</form>
</body>
</html>
