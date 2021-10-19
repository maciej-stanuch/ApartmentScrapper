package mail;

import com.google.inject.Singleton;
import scraper.Apartment;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class MailService {

    private final static Logger LOGGER = Logger.getLogger(MailService.class.getName());
    // private final static String ADDRESSES = AddressProvider.getAllAddressesString();

    public void sendMailWithApartments(Set<Apartment> apartments) throws MessagingException {
        // make it dynamic and ugly ;)
        String ADDRESSES = AddressProvider.getAllAddressesString();

        if (apartments == null || apartments.isEmpty()) {
            return;
        }

        Properties prop = new Properties();
	// mail server PG
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", true);
        prop.put("mail.smtp.host", "smtp.student.pg.edu.pl");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "*");
        prop.put("mail.smtp.connectiontimeout", "20000");
        prop.put("mail.smtp.timeout", "15000");

        LOGGER.log(Level.INFO, "Mail properties " + prop);

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("login", "password"); //pass for the mail server
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("<sender_address_here>"));
        message.setRecipients(
                Message.RecipientType.BCC, InternetAddress.parse(ADDRESSES)
        );
        message.setSubject("Mieszkania - " + java.time.LocalDateTime.now());

        StringBuilder stringBuilder = new StringBuilder();

        apartments.forEach(apartment -> {
            stringBuilder.append("******************************************************").append("\r\n");
            stringBuilder.append("Tytuł: ").append(apartment.getTitle().orElse("")).append("\r\n");
            stringBuilder.append("Lokalizacja: ").append(apartment.getLocation().orElse("")).append("\r\n");
            stringBuilder.append("Cena: ").append(apartment.getPrice().orElse("")).append("\r\n");
            stringBuilder.append("Link: ").append(apartment.getUrl().orElse("")).append("\r\n");
            stringBuilder.append("Powierzchnia: ").append(apartment.getArea().orElse("")).append("\r\n");
            stringBuilder.append("Pokoje: ").append(apartment.getRooms().orElse("")).append("\r\n");
            stringBuilder.append("Źródło: ").append(apartment.getSource()).append("\r\n")
                    .append("******************************************************")
                    .append("\r\n");
        });


        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(stringBuilder.toString(), "text/plain; charset=UTF-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        LOGGER.log(Level.INFO, "Sending message to " + ADDRESSES);
        Transport.send(message);
        LOGGER.log(Level.INFO, "Success");
    }

}
