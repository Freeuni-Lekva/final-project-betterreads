<%--
  Created by IntelliJ IDEA.
  User: GG
  Date: 8/4/2021
  Time: 10:12 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Already Registered</title>
    <link href="/Style/RegistrationStyle.css" type="text/css" rel="stylesheet">
</head>
<body>



<div class="sing-up-form">
    <h3>  <%=request.getAttribute("Error")%> </h3>
    <h3>Please Try Again</h3>
    <form action="register" method="post">
        <input type="text" class="input-box" placeholder="First name" name="first_name">
        <input type="text" class="input-box" placeholder="Last name" name="last_name">
        <input type="text" class="input-box" placeholder="Username " name="username">
        <input type="text" class="input-box" placeholder="Email" name="email">
        <input type="password" class="input-box" placeholder="Password" name="password"> <br><br>
        <button type="submit" class="register"> Sign up</button>
    </form>
</div>

</body>
</html>
