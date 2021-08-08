import Model.Book;
import Service.BookService;
import Service.BookServiceSort;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BookServiceSortTest {
    private List<Book> list;
    private Book book1;
    private Book book2;
    private Book book3;

    private void createBooks(){
        list = new ArrayList<>();
        book1 = new Book();
        book1 = new Book();
        book1.setBook_name("Harry Potter");
        book1.setBook_description("oboli bichis tavgadasavali romelic jadokari agmochndeba");
        book1.setAuthor_id(1);
        book1.setRelease_year(1980);
        book1.setAvailable_count(0);
        book1.setBook_rating(0);
        list.add(book1);

        book2 = new Book();
        book2 = new Book();
        book2.setBook_name("Mglebi");
        book2.setAuthor_id(3);
        book2.setBook_description("Am cxovrebashi mgeli unda iyo da mgelze ufro mgeli");
        book2.setRelease_year(1999);
        book2.setAvailable_count(1);
        book2.setBook_rating(1);
        list.add(book2);

        book3 = new Book();
        book3 = new Book();
        book3.setBook_name("Naruto Uzumaki");
        book3.setBook_description("oboli bichis tavgadasavali romelic hokage agmochndeba");
        book3.setAuthor_id(2);
        book3.setRelease_year(2002);
        book3.setAvailable_count(2);
        book3.setBook_rating(2);
        list.add(book3);

    }


    @Test
    public void testGetOldToNew() throws SQLException {
        BookServiceSort bs = new BookServiceSort();
        createBooks();
        List<Book> l = bs.oldToNew(list);

        assertEquals(book1, l.get(0));
        assertEquals(book2, l.get(1));
        assertEquals(book3, l.get(2));
        Collections.reverse(list);
        l.get(0).setRelease_year(1999);
        List<Book> l2 = bs.oldToNew(l);
        assertEquals(book1, l2.get(0));;
    }

    @Test
    public void testGetNewToOld() throws SQLException {
        BookServiceSort bs = new BookServiceSort();
        createBooks();
        List<Book> l = bs.newToOld(list);
        assertEquals(book1, l.get(2));
        assertEquals(book2, l.get(1));
        assertEquals(book3, l.get(0));
    }

    @Test
    public void testSortLowToHighRating(){
        BookServiceSort bs = new BookServiceSort();
        createBooks();
        List<Book> l = bs.sortLowToHigh(list);
        assertEquals(book1, l.get(0));
        assertEquals(book2, l.get(1));
        assertEquals(book3, l.get(2));
        Collections.reverse(list);
        l.get(0).setBook_rating(1);
        List<Book> l2 = bs.sortLowToHigh(l);
        assertEquals(book1, l2.get(0));
    }

    @Test
    public void testSortHighToLowRating(){
        BookServiceSort bs = new BookServiceSort();
        createBooks();
        List<Book> l = bs.sortHighToLow(list);
        assertEquals(book1, l.get(2));
        assertEquals(book2, l.get(1));
        assertEquals(book3, l.get(0));
    }

    @Test
    public void testRemoveUnavailableBooks(){
        BookServiceSort bs = new BookServiceSort();
        createBooks();
        List<Book> l = bs.removeUnavailableBooks(list);
        assertEquals(book2, l.get(0));
        assertEquals(book3, l.get(1));
    }

}
