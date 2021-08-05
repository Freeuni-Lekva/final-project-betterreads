package Servlets;

import Constants.SharedConstants;
import Model.Book;
import Service.AllServices;
import Service.BookService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CatalogueServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/Catalogue.jsp").forward(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AllServices allServices = (AllServices) getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        BookService bs = allServices.getBookService();
        String yearSort = request.getParameter("booksYear");
        String ratingSort = request.getParameter("bookRating");
        List<Book> list = bs.getAllBooks();
        if(yearSort != null) {
            if (yearSort.equals("old to new")) {
                list = bs.oldToNew();
            } else if (yearSort.equals("new to old")) {
                list = bs.newToOld();
            }
        } else {
            if (ratingSort != null) {
                if (ratingSort.equals("High -> Low")) {
                    list = bs.getBestBooks(1, list.size() - 1);
                } else if (ratingSort.equals("Low -> High")) {
                    list = bs.getBestBooks(1, list.size() - 1);
                    list = bs.getLowRatingBooks(1, list.size() - 1);
                }
            }
        }
        request.setAttribute("list", list);
        request.getRequestDispatcher("WEB-INF/Catalogue.jsp").forward(request,response);
    }
}
