package Servlets;

import Constants.SharedConstants;
import Model.Book;
import Model.User;
import Service.AllServices;
import Service.ReviewService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteReviewServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AllServices allServices = (AllServices) getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        ReviewService reviewService = allServices.getReviewService();
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute(SharedConstants.SESSION_ATTRIBUTE);
        int review_id = Integer.parseInt(request.getParameter("reviewId"));

        String book_id = request.getParameter("bookID");
        String all_reviews = request.getParameter("all_reviews");
        boolean user_reviews = false;
        if(all_reviews.equals("false")) user_reviews = true;

        if(request.getParameter("delete_review") != null){
            try {
                reviewService.deleteReview(review_id);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        Book b = allServices.getBookService().getBookById(Integer.parseInt(book_id));
        request.setAttribute("bookID", b.getBook_id());
        request.setAttribute("bookName", b.getBook_name());
        request.setAttribute("authorId", b.getAuthor_id());
        request.setAttribute("description", b.getBook_description());
        request.setAttribute("rating", b.getBook_rating());
        request.setAttribute("count",b.getAvailable_count());
        request.setAttribute("year", b.getRelease_year());
        request.setAttribute("photo",b.getBook_photo());
        request.setAttribute("USER_REVIEWS_ONLY", user_reviews);
        request.getRequestDispatcher("/WEB-INF/BookPage.jsp").forward(request, response);
    }
}
