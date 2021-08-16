package Servlets;

import Constants.SharedConstants;
import Model.Book;
import Model.User;
import Service.AllServices;
import Service.RatingService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class BookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String bookId = httpServletRequest.getParameter("bookId");
        AllServices allServices = (AllServices) getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        Book b = allServices.getBookService().getBookById(Integer.parseInt(bookId));
        HttpSession httpSession = httpServletRequest.getSession();
        User user = (User) httpSession.getAttribute(SharedConstants.SESSION_ATTRIBUTE);
        httpServletRequest.setAttribute("bookID", b.getBook_id());
        httpServletRequest.setAttribute("bookName", b.getBook_name());
        httpServletRequest.setAttribute("authorName", allServices.getBookService().getAuthorById(b.getBook_id()).getAuthor_name());
        httpServletRequest.setAttribute("description", b.getBook_description());
        httpServletRequest.setAttribute("rating", b.getBook_rating());
        if(user != null){
            RatingService ratingService = allServices.getRatingService();
            httpServletRequest.setAttribute("user_rating", ratingService.getRatingForBookByUser(user.getUser_id(), b.getBook_id()));
        }
        else
            httpServletRequest.setAttribute("user_rating", 0);

        httpServletRequest.setAttribute("count",b.getAvailable_count());
        httpServletRequest.setAttribute("year", b.getRelease_year());
        httpServletRequest.setAttribute("photo",b.getBook_photo());
        httpServletRequest.setAttribute("USER_REVIEWS_ONLY", (Boolean)false);
        httpServletRequest.getRequestDispatcher("/WEB-INF/BookPage.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
