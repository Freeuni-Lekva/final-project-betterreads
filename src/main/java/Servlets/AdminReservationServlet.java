package Servlets;

import Constants.SharedConstants;
import Model.Book;
import Model.Reservation;
import Model.User;
import Service.AllServices;
import Service.ReservationService;
import Service.SendMailService;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminReservationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        AllServices allServices = (AllServices) getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        ReservationService reservationService = allServices.getReservationService();
        SendMailService mailService = allServices.getMailService();
        List<Reservation> reservations = allServices.getReservationService().getAllReservations();
        if(httpServletRequest.getParameter("resId") != null) {
            int delete = Integer.parseInt(httpServletRequest.getParameter("resId"));
            for (Reservation r : reservations) {
                if (delete == r.getReservationId()) {
                    Reservation reservation = reservationService.getReservationById(Integer.parseInt(httpServletRequest.getParameter("resId")));
                    reservationService.removeReservation(Integer.parseInt(httpServletRequest.getParameter("resId")));
                    User user = reservation.getUser();
                    Book book = reservation.getReservedBook();
                    String text = mailService.returnBookString(book,user);
                    try {
                        mailService.sendMail(user.getEmail(),"Book Returned",text);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        reservations = allServices.getReservationService().getAllReservations();
        httpServletRequest.setAttribute("reservations", reservations);
        httpServletRequest.getRequestDispatcher("WEB-INF/AdminRemoveReservation.jsp").forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        System.out.println("post???");
        AllServices allServices = (AllServices) getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        ReservationService reservationService = allServices.getReservationService();
        List<Reservation> reservations = allServices.getReservationService().getAllReservations();
        int delete = Integer.parseInt(httpServletRequest.getParameter("resId"));
        for(Reservation r : reservations){
            if(delete == r.getReservationId()){
                    int bookID = allServices.getReservationService().
                            getReservationById(r.getReservationId()).getReservedBook().getBook_id();
                    allServices.getBookService().setBookCOunt(bookID, allServices.getBookService().getBookCount(bookID) + 1);
                    reservationService.removeReservation(Integer.parseInt(httpServletRequest.getParameter("resId")));

            }
        }
        reservations = allServices.getReservationService().getAllReservations();
        httpServletRequest.setAttribute("reservations", reservations);
        httpServletRequest.getRequestDispatcher("WEB-INF/AdminRemoveReservation.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
