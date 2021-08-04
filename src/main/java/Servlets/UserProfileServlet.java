package Servlets;

import Constants.SharedConstants;
import Model.User;
import Service.AllServices;
import Service.UserBooksService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_id_str = (String) request.getParameter("userId");
        int user_id = Integer.parseInt(user_id_str);
        AllServices allServices = (AllServices) getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        UserBooksService booksService = allServices.getUserBooksService();
        User user = (User) request.getSession().getAttribute(SharedConstants.SESSION_ATTRIBUTE);
        request.setAttribute("name",user.getFirst_name());
        request.setAttribute("lastName",user.getLast_name());
        request.setAttribute("username",user.getUsername());
        request.setAttribute("email",user.getEmail());
        request.setAttribute("reserved",booksService.getReservedBooks(user_id));
        request.setAttribute("read",booksService.getAlreadyReadBooks(user_id));
        request.setAttribute("marked",booksService.getBooksForFuture(user_id));

        request.getRequestDispatcher("WEB-INF/UserProfile.jsp").forward(request,response);

    }

}
