package Service;

import Model.Book;
import Model.User;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class SendMailService {

    public void sendMail(String recipient, String subject, String text) throws MessagingException {
        String host = "smtp.gmail.com";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        final String email = "betterreadscontact@gmail.com";
        final String password = "wawiukiq";
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email,password);
            }
        });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(email));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(recipient));
        message.setSubject(subject);
        message.setText(text);
        Transport.send(message);

    }
    public String reservedBookText(Book book, User user){
        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy");
        Date d1 = new Date();
        Calendar cl = Calendar.getInstance();
        cl.setTime(d1);
        cl.add(Calendar.WEEK_OF_MONTH, 2);
        String afterTwoWeeks = format.format(cl.getTime()).toString();
        StringBuilder text = new StringBuilder();
        text.append("Hello " + user.getFirst_name() + " " + user.getLast_name() + "!\n");
        text.append("You just reserved " + book.getBook_name() + " from our library.\n");
        text.append("Please return the book in 2 weeks from now at " + afterTwoWeeks + "\n");
        text.append("Thank You!");
        return text.toString();
    }

    public String returnBookString(Book book, User user){
        StringBuilder text = new StringBuilder();
        text.append("Hello " + user.getFirst_name() + " " + user.getLast_name() + "!\n");
        text.append("Thank you for returning " + book.getBook_name() + ".\n");
        text.append("We hope you enjoyed reading it.\n");
        text.append("Thanks for being our costumer!\n");
        return text.toString();
    }



}
