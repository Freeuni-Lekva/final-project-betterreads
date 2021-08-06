package Servlets;

import Constants.SharedConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServletAdmin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute(SharedConstants.ADMIN_SESSION);
        request.getSession().removeAttribute(SharedConstants.SESSION_ATTRIBUTE);
        request.getRequestDispatcher("WEB-INF/HomePage.jsp").forward(request,response);
    }
}
