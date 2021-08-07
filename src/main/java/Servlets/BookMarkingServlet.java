package Servlets;

import Constants.SharedConstants;
import Model.Book;
import Model.User;
import Service.AllServices;
import Service.BookService;
import Service.SendMailService;
import Service.UserBooksService;

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
        if(book_ID != null) {
            if(httpServletRequest.getParameter("mark") != null)
                ubs.addBooksForFuture(user.getUser_id(), Integer.parseInt(book_ID));
            else if(httpServletRequest.getParameter("reserve") != null){
                ubs.addReservedBook(user.getUser_id(), Integer.parseInt(book_ID));
                Book book;
                try {
                    book = allServices.getBookService().getBookById(Integer.parseInt(book_ID));
                    String mailText = mailService.reservedBookText(book,user);
                    mailService.sendMail(user.getEmail(),"Book Reservation",mailText);
                } catch (MessagingException  | SQLException e) {
                    e.printStackTrace();
                }
            } else if(httpServletRequest.getParameter("unmark") != null)
                ubs.removeBookFromFuture(user.getUser_id(), Integer.parseInt(book_ID));
        }
        String bookId = httpServletRequest.getParameter("bookId");
        try {
            Book b = allServices.getBookService().getBookById(Integer.parseInt(bookId));
            httpServletRequest.setAttribute("bookID", b.getBook_id());
            httpServletRequest.setAttribute("bookName", b.getBook_name());
            httpServletRequest.setAttribute("authorId", b.getAuthor_id());
            httpServletRequest.setAttribute("description", b.getBook_description());
            httpServletRequest.setAttribute("rating", b.getBook_rating());
            httpServletRequest.setAttribute("count",b.getAvailable_count());
            httpServletRequest.setAttribute("year", b.getRelease_year());
            httpServletRequest.setAttribute("photo",b.getBook_photo());
            httpServletRequest.setAttribute("USER_REVIEWS_ONLY", false);
            httpServletRequest.getRequestDispatcher("/WEB-INF/BookPage.jsp").forward(httpServletRequest, httpServletResponse);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
