package Servlets;

import Constants.SharedConstants;
import Model.Book;
import Model.User;
import Service.AllServices;
import Service.UserBooksService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class UserReviewsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AllServices allServices = (AllServices) getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        UserBooksService ubs = allServices.getUserBooksService();
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute(SharedConstants.SESSION_ATTRIBUTE);
        String bookId = request.getParameter("bookID");
        try {
            Book b = allServices.getBookService().getBookById(Integer.parseInt(bookId));
            request.setAttribute("bookID", b.getBook_id());
            request.setAttribute("bookName", b.getBook_name());
            request.setAttribute("authorId", b.getAuthor_id());
            request.setAttribute("description", b.getBook_description());
            request.setAttribute("rating", b.getBook_rating());
            request.setAttribute("count",b.getAvailable_count());
            request.setAttribute("year", b.getRelease_year());
            request.setAttribute("photo",b.getBook_photo());
            request.setAttribute("USER_REVIEWS_ONLY", false);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(user != null){
            if(request.getParameter("user_reviews") != null){
                request.setAttribute("USER_REVIEWS_ONLY", true);
            }
            if(request.getParameter("all_reviews") != null){
                request.setAttribute("USER_REVIEWS_ONLY", false);
            }
        } else {
            request.setAttribute("USER_REVIEWS_ONLY", false);
        }

        request.getRequestDispatcher("/WEB-INF/BookPage.jsp").forward(request, response);
    }
}
