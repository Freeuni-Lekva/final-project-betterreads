package Servlets;

import Constants.SharedConstants;
import Model.Reservation;
import Service.AllServices;
import Service.ReservationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminReservationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        //System.out.println("aqvarrr");
        AllServices allServices = (AllServices) getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        ReservationService reservationService = allServices.getReservationService();
        List<Reservation> reservations = allServices.getReservationService().getAllReservations();
        if(httpServletRequest.getParameter("resId") != null) {
            int delete = Integer.parseInt(httpServletRequest.getParameter("resId"));
            System.out.println("searching " + delete);
            for (Reservation r : reservations) {
                System.out.println("now" + r.getReservationId());
                if (delete == r.getReservationId()) {
                    reservationService.removeReservation
                            (Integer.parseInt(httpServletRequest.getParameter("resId")));
                }
            }
        }
        reservations = allServices.getReservationService().getAllReservations();
        httpServletRequest.setAttribute("reservations", reservations);
        httpServletRequest.getRequestDispatcher("WEB-INF/AdminRemoveReservation.jsp").forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        AllServices allServices = (AllServices) getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        ReservationService reservationService = allServices.getReservationService();
        List<Reservation> reservations = allServices.getReservationService().getAllReservations();
        int delete = Integer.parseInt(httpServletRequest.getParameter("resId"));
        System.out.println("searching " + delete);
        for(Reservation r : reservations){
            System.out.println("now"  + r.getReservationId());
            if(delete == r.getReservationId()){
                reservationService.removeReservation
                        (Integer.parseInt(httpServletRequest.getParameter("resId")));
            }
        }
        reservations = allServices.getReservationService().getAllReservations();
        httpServletRequest.setAttribute("reservations", reservations);
        httpServletRequest.getRequestDispatcher("WEB-INF/AdminRemoveReservation.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
