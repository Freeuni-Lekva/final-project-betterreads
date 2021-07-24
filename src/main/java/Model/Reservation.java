package Model;

import java.sql.Date;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return getReservationId() == that.getReservationId() && getReservedBook().equals(that.getReservedBook()) && getUser().equals(that.getUser()) && getDeadline().equals(that.getDeadline());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReservationId(), getReservedBook(), getUser(), getDeadline());
    }
}
