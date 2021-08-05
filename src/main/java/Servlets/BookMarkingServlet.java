package Servlets;

import Constants.SharedConstants;
import Model.User;
import Service.AllServices;
import Service.BookService;
import Service.UserBooksService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class BookMarkingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        AllServices allServices = (AllServices) getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        UserBooksService ubs = allServices.getUserBooksService();
        HttpSession httpSession = httpServletRequest.getSession();
        User user = (User) httpSession.getAttribute(SharedConstants.SESSION_ATTRIBUTE);
        String book_ID = httpServletRequest.getParameter("bookID");
        if(book_ID != null) {
            System.out.println("not null");
            System.out.println(user.getUser_id());
            if(httpServletRequest.getParameter("mark") != null)
                ubs.addBooksForFuture(user.getUser_id(), Integer.parseInt(book_ID));
            else if(httpServletRequest.getParameter("reserve") != null)
                ubs.addReservedBook(user.getUser_id(), Integer.parseInt(book_ID));
        }
        httpServletRequest.getRequestDispatcher("/WEB-INF/BookPage.jsp").forward(httpServletRequest, httpServletResponse);

    }
}
