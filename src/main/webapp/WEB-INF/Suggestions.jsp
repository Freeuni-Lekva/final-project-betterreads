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

<ul>
    <li>
        <c:forEach items="${list}" var="book">
            <jsp:include page="BookPreview.jsp"/>
        </c:forEach>
    </li>
</ul>
</body>
</html>
