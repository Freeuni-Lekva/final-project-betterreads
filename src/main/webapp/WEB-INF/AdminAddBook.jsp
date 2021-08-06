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

       <button type="submit">Add Book</button>
   </form>

</body>
</html>