package Servlets;

import Constants.SharedConstants;
import Model.User;
import Service.AdminService;
import Service.AllServices;
import Service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

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
        AdminService adminService = allServices.getAdminService();
        HttpSession session = request.getSession();


        try {
            if(userService.checkUsernameExists(username) && userService.checkPassword(userService.getUserByUsername(username),password)){
                if(adminService.isAdmin(userService.getUserByUsername(username))){
                    session.setAttribute(SharedConstants.ADMIN_SESSION,userService.getUserByUsername(username));
                    session.setAttribute(SharedConstants.SESSION_ATTRIBUTE,userService.getUserByUsername(username));
                    request.getRequestDispatcher("WEB-INF/AdminHomePage.jsp").forward(request,response);
                    return;
                }
                if((User)session.getAttribute(SharedConstants.SESSION_ATTRIBUTE) == null)
                    session.setAttribute(SharedConstants.SESSION_ATTRIBUTE,userService.getUserByUsername(username));

                request.getRequestDispatcher("WEB-INF/HomePage.jsp").forward(request,response);
            } else if(userService.checkMailExists(username) && userService.checkPassword(userService.getUserByMail(username),password)){
                if(adminService.isAdmin(userService.getUserByMail(username))){
                    session.setAttribute(SharedConstants.ADMIN_SESSION,userService.getUserByMail(username));
                    session.setAttribute(SharedConstants.SESSION_ATTRIBUTE,userService.getUserByMail(username));
                    request.getRequestDispatcher("WEB-INF/AdminHomePage.jsp").forward(request,response);
                    return;
                }
                if((User)session.getAttribute(SharedConstants.SESSION_ATTRIBUTE) == null)
                    session.setAttribute(SharedConstants.SESSION_ATTRIBUTE,userService.getUserByMail(username));

                request.getRequestDispatcher("WEB-INF/HomePage.jsp").forward(request,response);
            } else {
                request.getRequestDispatcher("WEB-INF/IncorrectData.jsp").forward(request,response);
            }
        } catch (SQLException | NoSuchAlgorithmException throwables) {
            throwables.printStackTrace();
        }
    }
}
