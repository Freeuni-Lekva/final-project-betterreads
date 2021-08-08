package Servlets;

import Constants.SharedConstants;
import Model.Author;
import Model.Book;
import Service.AdminService;
import Service.AllServices;
import Service.GenreService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AddBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AllServices allServices = (AllServices) getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        GenreService genreService = allServices.getGenreService();
        List<String> genres = genreService.getGenres();
        request.setAttribute("genres", genres);
        request.getRequestDispatcher("/WEB-INF/AdminAddBook.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AllServices allServices = (AllServices) getServletContext().getAttribute(SharedConstants.ATTRIBUTE);
        AdminService adminService = allServices.getAdminService();

        if(request.getParameter("book_name").length() == 0 || request.getParameter("author_name").length() == 0
        || request.getParameter("release_year").length() == 0 || request.getParameter("count").length() == 0
        ||  request.getParameter("photo").length() == 0 || request.getParameter("book_description").length() == 0
        || request.getParameter("genre") == null){
            request.getRequestDispatcher("WEB-INF/BookNotAdded.jsp").forward(request,response);
            return;
        }
        String book_name = request.getParameter("book_name");
        String author_name = request.getParameter("author_name");
        Author author = null;
        try {
            author = adminService.getAuthorByName(author_name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(author == null){
            request.getRequestDispatcher("WEB-INF/BookNotAdded.jsp").forward(request,response);
            return;
        }
        int release_year = Integer.parseInt(request.getParameter("release_year"));
        int count = Integer.parseInt(request.getParameter("count"));
        String photo = request.getParameter("photo");
        String desc = request.getParameter("book_description");
        String[] genres = request.getParameterValues("genre");

        try {
            if(adminService.addBook(book_name,author,release_year,count,photo,desc,genres)){
                request.getRequestDispatcher("WEB-INF/BookAdded.jsp").forward(request,response);
            } else {
                request.getRequestDispatcher("WEB-INF/BookNotAdded.jsp").forward(request,response);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
