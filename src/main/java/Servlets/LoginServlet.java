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

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/LogIn.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        AllServices allServices = (AllServices) getServletContext().getAttribute(SharedConstants.ATTRIBUTE);

        UserService userService = allServices.getUserService();
        if(userService.checkUsernameExists(username)){
            User user = userService.getUserByUsername(username);
            if(userService.checkPassword(user,password)){
                request.getRequestDispatcher("WEB-INF/HomePage.jsp").forward(request,response);
            } else {
                request.getRequestDispatcher("WEB-INF/IncorrectData.jsp").forward(request,response);
            }
        } else if(userService.checkMailExists(username)){
            User user = userService.getUserByMail(username);
            if(userService.checkPassword(user,password)){
                request.getRequestDispatcher("WEB-INF/HomePage.jsp").forward(request,response);
            } else {
                request.getRequestDispatcher("WEB-INF/IncorrectData.jsp").forward(request,response);
            }
        } else {
            //this username or email does not exists
            request.getRequestDispatcher("WEB-INF/IncorrectData.jsp").forward(request,response);
        }
    }
}
