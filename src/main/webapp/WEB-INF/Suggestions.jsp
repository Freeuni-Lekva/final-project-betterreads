<%@ page import="java.util.List" %>
<%@ page import="Model.Book" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Vano
  Date: 06.08.2021
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>

<jsp:include page='Header.jsp'>
    <jsp:param name="Header" value="Header"/>
</jsp:include>

<%
    List<Book> list =(List<Book>) request.getAttribute("list");
    System.out.println(list.size());
%>

<ol>
    <li>
        <%
            for(Book b : list){
                request.setAttribute("book", b);
        %>
        <jsp:include page='BookPreview.jsp'>
            <jsp:param name="book" value="${b}"/>
        </jsp:include>
        <%
            }
        %>
    </li>
</ol>
</body>
</html>
