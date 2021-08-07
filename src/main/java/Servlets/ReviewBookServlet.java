package Servlets;

import Constants.SharedConstants;
import Model.Book;
import Model.User;
import Service.AllServices;
import Service.BookService;
import Service.ReviewService;
import Service.UserBooksService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReviewBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_comment = request.getParameter("review");
        int book_id = Integer.parseInt(request.getParameter("bookID"));
        User user = (User) request.getSession().getAttribute(SharedConstants.SESSION_ATTRIBUTE);
        AllServices allServices = (AllServices) getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        ReviewService reviewService = allServices.getReviewService();
        reviewService.addReview(user.getUser_id(), book_id, user_comment, LocalDate.now().toString(), 0);
        UserBooksService userBooksService = allServices.getUserBooksService();
        try {
            userBooksService.markBookAsRead(user.getUser_id(), book_id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // set attributes and forward same book page
        BookService bookService = allServices.getBookService();
        try {
            Book book = bookService.getBookById(book_id);
            request.setAttribute("bookID", book_id);

            request.setAttribute("bookName", book.getBook_name());
            request.setAttribute("authorId", book.getAuthor_id());
            request.setAttribute("description", book.getBook_description());
            request.setAttribute("rating", book.getBook_rating());
            request.setAttribute("count", book.getAvailable_count());
            request.setAttribute("year", book.getRelease_year());
            request.setAttribute("photo", book.getBook_photo());
            request.getRequestDispatcher("/WEB-INF/BookPage.jsp").forward(request, response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
