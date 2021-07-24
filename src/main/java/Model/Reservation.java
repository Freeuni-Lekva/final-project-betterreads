package Model;

import java.sql.Date;

public class Reservation {
    private int reservationId;
    private Book reservedBook;
    private User user;
    private Date deadline;

    public Reservation(){}

    public Reservation(int reservationId, Book reservedBook, User user, Date deadline){
        this.reservationId = reservationId;
        this.reservedBook = reservedBook;
        this.user = user;
        this.deadline = deadline;
    }

    public int getReservationId(){
        return reservationId;
    }

    public Book getReservedBook(){
        return reservedBook;
    }

    public User getUser() {
        return user;
    }

    public Date getDeadline(){
        return deadline;
    }

    public void setReservationId(int reservationId){
        this.reservationId = reservationId;
    }

    public void setReservedBook(Book reservedBook){
        this.reservedBook = reservedBook;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setDeadline(Date deadline){
        this.deadline = deadline;
    }
}
