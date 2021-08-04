package Servlets;

import Constants.SharedConstants;
import Model.User;
import Service.AllServices;
import Service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        if(userService.checkMailExists(email) || userService.checkUsernameExists(username)){
            request.getRequestDispatcher("WEB-INF/AlreadyRegistered.jsp").forward(request,response);
        } else {
            User newUser = new User();
            String hashedPassword = allServices.getHashService().hashPassword(password);
            newUser.setPassword_hash(hashedPassword);
            newUser.setUsername(username);
            newUser.setFirst_name(first_name);
            newUser.setLast_name(last_name);
            newUser.setEmail(email);
            if(userService.addUser(newUser)){
                request.getRequestDispatcher("WEB-INF/HomePage.jsp").forward(request,response);
            }
        }
    }
}
