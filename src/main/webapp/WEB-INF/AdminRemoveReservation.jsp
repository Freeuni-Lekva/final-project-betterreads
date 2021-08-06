<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="Model.Reservation" %>

<html>

<head>
   <title>Reservations</title>
</head>

<body>
   <h1>Reservations:</h1>
   <%
      List<Reservation> lst = (List<Reservation>)request.getAttribute("reservations");
   %>
   <form action="/adminReservations" method = "post">
      <%
        for(Reservation r : lst){
           request.setAttribute("reserveId", r.getReservationId());
           request.setAttribute("userId", r.getUser().getUser_id());
           request.setAttribute("bookId", r.getReservedBook().getBook_id());
      %>
        <a href="/adminReservations?resId=<%= r.getReservationId()%>"><%= r.getReservationId()%>
                </a><br>
      <%
        }
      %>
   </form>

</body>
</html>