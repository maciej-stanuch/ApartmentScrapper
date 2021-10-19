import com.google.common.collect.Sets;
import com.google.inject.Inject;
import mail.MailService;
import scraper.Apartment;
import scraper.ApartmentScrapperService;

import javax.mail.MessagingException;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    private final ApartmentScrapperService apartmentScrapperService;
    private final MailService mailService;
    private final Set<Apartment> apartmentsAlreadyFound = new HashSet<>();

    @Inject
    public App(ApartmentScrapperService apartmentScrapperService, MailService mailService) {
        this.apartmentScrapperService = apartmentScrapperService;
        this.mailService = mailService;
    }

    public void start() {
        LOGGER.log(Level.INFO, "Starting scrapper");

        for (;;) {
            try {
                Set<Apartment> newApartments = apartmentsDiff(apartmentScrapperService.getApartments());
                apartmentsAlreadyFound.addAll(newApartments);

                LOGGER.log(Level.INFO, "Sending mail with " + newApartments.size()  + " new apartments.");
                mailService.sendMailWithApartments(newApartments);

                Thread.sleep(Duration.ofMinutes(15).toMillis());
            } catch (InterruptedException | MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    private Set<Apartment> apartmentsDiff(Set<Apartment> apartments) {
        return new HashSet<>(Sets.difference(apartments, apartmentsAlreadyFound));
    }
}
