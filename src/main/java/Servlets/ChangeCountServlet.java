package Servlets;

import Constants.SharedConstants;
import Dao.AdminDao;
import Model.Book;
import Service.AdminService;
import Service.AllServices;
import Service.BookService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ChangeCountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AllServices allServices = (AllServices) getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        AdminService adminService = allServices.getAdminService();
        BookService bookService = allServices.getBookService();
        String bookId = request.getParameter("bookId");
        int count = Integer.parseInt(request.getParameter("count"));
        Book book = bookService.getBookById(Integer.parseInt(bookId));
        try {
            adminService.changeBookCount(book,count);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Book newBook = bookService.getBookById(Integer.parseInt(bookId));
        request.setAttribute("bookID", newBook.getBook_id());
        request.setAttribute("bookName", newBook.getBook_name());
        request.setAttribute("authorId", newBook.getAuthor_id());
        request.setAttribute("authorName", bookService.getAuthorById(newBook.getBook_id()).getAuthor_name());
        request.setAttribute("description", newBook.getBook_description());
        request.setAttribute("rating", newBook.getBook_rating());
        request.setAttribute("count", newBook.getAvailable_count());
        request.setAttribute("year", newBook.getRelease_year());
        request.setAttribute("photo", newBook.getBook_photo());
        request.setAttribute("USER_REVIEWS_ONLY", request.getParameter("USER_REVIEWS_ONLY"));
        request.setAttribute(SharedConstants.USER_REVIEWS,false);
        request.getRequestDispatcher("/WEB-INF/BookPage.jsp").forward(request, response);

    }
}
