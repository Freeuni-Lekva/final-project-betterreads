package Servlets;

import Constants.SharedConstants;
import Model.User;
import Service.AllServices;
import Service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        HttpSession session = request.getSession();
        if(userService.checkUsernameExists(username)){
            User user = userService.getUserByUsername(username);
            if(userService.checkPassword(user,password)){
                //
                User currUser = (User)session.getAttribute(SharedConstants.SESSION_ATTRIBUTE);
                if(currUser == null){
                    //System.out.println("new  user in session");
                    request.getSession().setAttribute(SharedConstants.SESSION_ATTRIBUTE,user);
                } else {
                    //System.out.println("already user in session");
                }
                request.getRequestDispatcher("WEB-INF/HomePage.jsp").forward(request,response);

            } else {
                request.getRequestDispatcher("WEB-INF/IncorrectData.jsp").forward(request,response);
            }
        } else if(userService.checkMailExists(username)){
            User user = userService.getUserByMail(username);
            if(userService.checkPassword(user,password)){
                User currUser = (User)session.getAttribute(SharedConstants.SESSION_ATTRIBUTE);
                if(currUser == null){

                    request.getSession().setAttribute(SharedConstants.SESSION_ATTRIBUTE,user);

                } else {
                    //System.out.println("already user in session");
                }
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
