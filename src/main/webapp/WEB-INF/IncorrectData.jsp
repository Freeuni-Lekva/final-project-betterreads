<%--
  Created by IntelliJ IDEA.
  User: etsir
  Date: 8/3/2021
  Time: 11:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Incorrect</title>
</head>
<body>

<form action="/home" method = "post">
    <button type="submit"> BetterReads </button>
</form>

<h3>Please Try Again</h3>
<h4>Your username or password is incorrect</h4>
<form action="login" method="post">
    <table style="width: 20%">
        <tr>
            <td>Username/Email:</td>
            <td><input type="text" name="username" /></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password" /></td>
        </tr>
    </table>
    <input type="submit" value="Submit" /></form>
</body>
</html>
