package Servlets;

import Constants.SharedConstants;
import Model.Book;
import Service.AllServices;
import Service.GenreService;
import Service.SearchService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class searchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = request.getParameter("stext");
        System.out.println(text);
        AllServices allServices = (AllServices) getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        SearchService searchService = allServices.getSearchService();
        List<Book> bookList = searchService.filterByText(text);
        request.setAttribute("list", bookList);
        request.getRequestDispatcher("WEB-INF/Catalogue.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
