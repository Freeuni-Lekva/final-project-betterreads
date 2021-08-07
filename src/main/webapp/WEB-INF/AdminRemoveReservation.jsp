<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="Model.Reservation" %>

<html>

<head>
   <title>Reservations</title>
</head>

<body>
   <jsp:include page='AdminHeader.jsp'>
       <jsp:param name="AdminHeader" value="AdminHeader"/>
   </jsp:include>

   <h1>Reservations:</h1>
   <%
      List<Reservation> lst = (List<Reservation>)request.getAttribute("reservations");
   %>
   <form action="/adminReservations" method = "post">
      <table style="width:100%">
        <tr>
          <th>Reservation ID</th>
          <th>User ID</th>
          <th>Reserved Book ID</th>
          <th>Deadline</th>
        </tr>
        <%
                for(Reservation r : lst){
                   request.setAttribute("reserveId", r.getReservationId());
                   request.setAttribute("userId", r.getUser().getUser_id());
                   request.setAttribute("bookId", r.getReservedBook().getBook_id());
              %>

              <tr>
                 <th><%= r.getReservationId() %></th>
                 <th><%= r.getUser().getUser_id() %></th>
                 <th><%= r.getReservedBook().getBook_id() %></th>
                 <th><%= r.getDeadline() %><th>
                 <th><a href="/adminReservations?resId=<%= r.getReservationId()%>">Remove reservation</a><th>
              </tr>

              <%
                }
              %>
      </table>

   </form>

</body>
</html>