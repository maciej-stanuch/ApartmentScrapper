import com.google.inject.Inject;
import scraper.Apartment;
import scraper.ApartmentScrapperService;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    private final ApartmentScrapperService apartmentScrapperService;

    @Inject
    public App(ApartmentScrapperService apartmentScrapperService) {
        this.apartmentScrapperService = apartmentScrapperService;
    }

    public void start() {
        LOGGER.log(Level.INFO, "Starting scrapper");
        Set<Apartment> apartmentSet = apartmentScrapperService.getApartments();
        LOGGER.log(Level.INFO, "Scrapped " + apartmentSet.size() + " apartments.");

        apartmentSet.forEach(apartment -> {
            System.out.println("Title: " + apartment.getTitle().orElse(""));
            System.out.println("Location: " + apartment.getLocation().orElse(""));
            System.out.println("Price: " + apartment.getPrice().orElse(""));
            System.out.println("Link: " + apartment.getUrl().orElse(""));
            System.out.println("Area: " + apartment.getArea().orElse(""));
            System.out.println("Rooms: " + apartment.getRooms().orElse(""));
            System.out.println("Source: " + apartment.getSource());
            System.out.println("******************************************************");
            System.out.println();
            System.out.println("******************************************************");
        });
    }
}
