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
        List<Book> list = bs.getAllBooks();

        if (request.getParameter("Available") != null){
            list = bs.removeUnavailableBooks(list);
        }

        if(request.getParameter("SortBooks") != null){
            String sortName = request.getParameter("SortBooks");
            if (sortName.equals("old to new")) {
                list = bs.oldToNew(list);
            } else if (sortName.equals("new to old")) {
                list = bs.newToOld(list);
            } else if (sortName.equals("High -> Low")) {
                list = bs.sortHighToLow(list);
            } else if (sortName.equals("Low -> High")) {
                list = bs.sortLowToHigh(list);
            }
        }


        if (request.getParameter("GenreFilter") != null){
            String[] names = request.getParameterValues("genres");
            list = bs.getBooksByGanres(names);
        }
        request.setAttribute("list", list);
        request.getRequestDispatcher("WEB-INF/Catalogue.jsp").forward(request,response);
    }
}
