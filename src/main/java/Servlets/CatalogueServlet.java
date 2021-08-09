package Servlets;

import Constants.SharedConstants;
import Model.Book;
import Model.Genre;
import Service.AllServices;
import Service.BookService;
import Service.BookServiceSort;
import Service.GenreService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CatalogueServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AllServices allServices = (AllServices) getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        GenreService genreService = allServices.getGenreService();
        List<String> genres = genreService.getGenres();
        request.setAttribute("genres", genres);
        BookService bs = allServices.getBookService();
        List<Book> list = bs.getAllBooks();
        request.setAttribute("books", list);
        request.getRequestDispatcher("WEB-INF/Catalogue.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AllServices allServices = (AllServices) getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        BookService bs = allServices.getBookService();
        BookServiceSort bss = allServices.getBookServiceSort();
        List<Book> list = bs.getAllBooks();

        if (request.getParameter("Available") != null){
            list = bss.removeUnavailableBooks(list);
        }

        if(request.getParameter("SortBooks") != null){
            String sortName = request.getParameter("SortBooks");
            if (sortName.equals("old to new")) {
                try {
                    list = bss.oldToNew(list);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else if (sortName.equals("new to old")) {
                try {
                    list = bss.newToOld(list);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else if (sortName.equals("High -> Low")) {
                list = bss.sortHighToLow(list);
            } else if (sortName.equals("Low -> High")) {
                list = bss.sortLowToHigh(list);
            }
        }

        if (request.getParameter("genre") != null){
            String[] names = request.getParameterValues("genre");
            list = bs.getBooksByGanres(names, list);
        }
        request.setAttribute("books", list);
        GenreService genreService = allServices.getGenreService();
        List<String> genres = genreService.getGenres();
        request.setAttribute("genres", genres);
        request.getRequestDispatcher("WEB-INF/Catalogue.jsp").forward(request,response);
    }
}
