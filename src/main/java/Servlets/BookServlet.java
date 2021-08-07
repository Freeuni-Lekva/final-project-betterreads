package Servlets;

import Constants.SharedConstants;
import Model.Book;
import Service.AllServices;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class BookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String bookId = httpServletRequest.getParameter("bookId");
        AllServices allServices = (AllServices) getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
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
            httpServletRequest.getRequestDispatcher("/WEB-INF/BookPage.jsp").forward(httpServletRequest, httpServletResponse);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
