package Servlets;

import Constants.SharedConstants;
import Model.Book;
import Model.User;
import Service.*;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class BookMarkingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        AllServices allServices = (AllServices) getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        UserBooksService ubs = allServices.getUserBooksService();
        SendMailService mailService = allServices.getMailService();
        HttpSession httpSession = httpServletRequest.getSession();
        User user = (User) httpSession.getAttribute(SharedConstants.SESSION_ATTRIBUTE);
        String book_ID = httpServletRequest.getParameter("bookID");
        if (book_ID != null) {
            if (httpServletRequest.getParameter("mark") != null) {
                ubs.addBooksForFuture(user.getUser_id(), Integer.parseInt(book_ID));
            } else if (httpServletRequest.getParameter("reserve") != null) {
                int currCount = 0;
                currCount = allServices.getBookService().getBookCount(Integer.parseInt(book_ID));
                if (currCount > 0) {
                    allServices.getBookService().setBookCOunt(Integer.parseInt(book_ID), currCount - 1);
                    try {
                        ubs.addReservedBook(user.getUser_id(), Integer.parseInt(book_ID));
                        Book book;
                        book = allServices.getBookService().getBookById(Integer.parseInt(book_ID));
                        String mailText = mailService.reservedBookText(book, user);
                        mailService.sendMail(user.getEmail(), "Book Reservation", mailText);

                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                } else {
                }
            } else if (httpServletRequest.getParameter("unmark") != null) {
                ubs.removeBookFromFuture(user.getUser_id(), Integer.parseInt(book_ID));

            }

            String bookId = httpServletRequest.getParameter("bookId");
            Book b = allServices.getBookService().getBookById(Integer.parseInt(bookId));
            httpServletRequest.setAttribute("bookID", b.getBook_id());
            httpServletRequest.setAttribute("bookName", b.getBook_name());
            httpServletRequest.setAttribute("authorId", b.getAuthor_id());
            httpServletRequest.setAttribute("authorName", allServices.getBookService().getAuthorById(b.getBook_id()).getAuthor_name());
            httpServletRequest.setAttribute("description", b.getBook_description());
            httpServletRequest.setAttribute("rating", b.getBook_rating());
            if(user != null){
                RatingService ratingService = allServices.getRatingService();
                httpServletRequest.setAttribute("user_rating", ratingService.getRatingForBookByUser(user.getUser_id(), b.getBook_id()));
            }
            else
                httpServletRequest.setAttribute("user_rating", 0);
            httpServletRequest.setAttribute("count", b.getAvailable_count());
            httpServletRequest.setAttribute("year", b.getRelease_year());
            httpServletRequest.setAttribute("photo", b.getBook_photo());
            httpServletRequest.setAttribute("USER_REVIEWS_ONLY", false);
            httpServletRequest.getRequestDispatcher("/WEB-INF/BookPage.jsp").forward(httpServletRequest, httpServletResponse);

        }
    }
}