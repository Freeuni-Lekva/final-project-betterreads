<%--
  Created by IntelliJ IDEA.
  User: etsir
  Date: 8/3/2021
  Time: 8:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="/Style/LoginStyle.css" type="text/css" rel="stylesheet">
    <title>LogIn</title>
</head>
<body>

<div class="sing-up-form">
    <h1> Login </h1>
    <form action="login" method="post">
        <input type="text" class="input-box" placeholder="Email/Username" name="username">
        <input type="password" class="input-box" placeholder="Password" name="password"> <br><br>
        <button type="submit" class="login"> Sign in</button>   
        <div class ="signup_link">
            Not a member? <a href="/register">Sign up</a>
        </div>
    </form>
</div>


</body>
</html>
