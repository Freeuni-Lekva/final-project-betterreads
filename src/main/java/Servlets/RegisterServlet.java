package Servlets;

import Constants.SharedConstants;
import Model.User;
import Service.AllServices;
import Service.UserService;
import Service.VallidationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/Registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        AllServices allServices = (AllServices) getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        UserService userService = allServices.getUserService();

        VallidationService vallService = allServices.getVallService();
        String errorMessage = vallService.getErrorMessage(first_name, last_name, email, password, username);
        if(!errorMessage.isEmpty()){
            request.setAttribute("Error", errorMessage);
            request.getRequestDispatcher("WEB-INF/AlreadyRegistered.jsp").forward(request, response);
        } else  if(userService.checkUsernameExists(username) || userService.checkMailExists(email)){
            errorMessage = "The specified account already exists";
            request.setAttribute("Error", errorMessage);
            request.getRequestDispatcher("WEB-INF/AlreadyRegistered.jsp").forward(request, response);
        } else {
            String hashedPassword = allServices.getHashService().hashPassword(password);
            if(userService.addUser(first_name, last_name, username, hashedPassword, email)){
                User user = userService.getUserByMail(email);
                HttpSession session = request.getSession();
                User currUser = (User)session.getAttribute(SharedConstants.SESSION_ATTRIBUTE);
                if(currUser == null) {
                    request.getSession().setAttribute(SharedConstants.SESSION_ATTRIBUTE, user);
                }
                request.getRequestDispatcher("WEB-INF/HomePage.jsp").forward(request, response);
            }
        }
    }
}
