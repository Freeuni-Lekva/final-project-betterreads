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

    <meta  name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/Style/Home.css" type="text/css" rel="stylesheet">


</head>
<body>
<%
    AllServices allServices = (AllServices) pageContext.getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
    BookService bb = allServices.getBookService();
    List<Book> list = bb.getBestBooks(1, 3);
%>
    <div class="container">
        <div class="navbar">
            <img src="https://scontent.ftbs5-2.fna.fbcdn.net/v/t1.15752-9/122933159_1731951446971698_8170084241773394004_n.png?_nc_cat=104&ccb=1-4&_nc_sid=ae9488&_nc_eui2=AeGDdGwsVvAZvv_Q5fiqe3055gVjItJHnXLmBWMi0kedchYRPajl59i3SmakETsrifiC1ORr-Q5HUggh4hz2nFsI&_nc_ohc=jNNnVzWLFswAX9rq2Nl&_nc_ht=scontent.ftbs5-2.fna&oh=1efade4ee2372eba5c55f0c09f2ce2e6&oe=6132A41D" class="logo">
            <nav>
                <ul>
                    <li> <h2> Most popular books right now: </h2>/li>
                    <li><a href="/home"> Home </a></li>
                    <li><a href="/catalogue"> Catalogue </a></li>
                    <%
                        HttpSession httpSession = pageContext.getSession();
                        User user = (User) httpSession.getAttribute(SharedConstants.SESSION_ATTRIBUTE);
                        if(user == null){
                    %>
                    <li><a href="/login"> Login </a></li>
                    <li><a href="/register"> Sign up </a></li>
                    <%
                    } else {
                    %>
                    <li> <a href="userProfile?userId=<%= user.getUser_id()%>"> Visit your profile </a> </li>

                    <li><a href="/logout"> Log out </a></li>
                    <%
                        }
                    %>
                </ul>
            </nav>
        </div>

        <div class="row">
            <div class="col">
                <h1>BetterReads</h1>
                <form action="/search" method = "post">
                    <div class="search">
                    <label for="stext">Search books:</label>
                    <input type="text" placeholder="  Explore more books.."class="round"id="stext" name="stext"  >
                    <button type="submit"> Search</button>
                    </div>
                </form>
            </div>


            <div class="row">

                <div class="column">
                    <a href="showBook?bookId=<%= list.get(0).getBook_id()%>"><%= list.get(0).getBook_name() %>
                    <img src=<%= list.get(0).getBook_photo()%>  style: width="100" height="150">
                </div>

                <div class="column">
                    <a href="showBook?bookId=<%= list.get(1).getBook_id()%>"><%= list.get(1).getBook_name() %>
                        <img src=<%= list.get(1).getBook_photo()%> style: width="100" height="150">
                </div>

                <div class="column">
                    <a href="showBook?bookId=<%= list.get(2).getBook_id()%>"><%= list.get(2).getBook_name() %>
                        <img src=<%= list.get(2).getBook_photo()%>  style: width="100" height="150">
                </div>
            </div>
        </div>
    </div>

</body>
</html>
