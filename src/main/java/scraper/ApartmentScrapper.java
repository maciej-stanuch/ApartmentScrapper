package scraper;

import java.io.IOException;
import java.util.Set;

public interface ApartmentScrapper {
    Set<Apartment> scrapApartments() throws IOException;
}
