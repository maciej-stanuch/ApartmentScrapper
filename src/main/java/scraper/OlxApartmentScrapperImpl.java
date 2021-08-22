package scraper;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class OlxApartmentScrapperImpl implements ApartmentScrapper {

    private final static Logger LOGGER = Logger.getLogger(OlxApartmentScrapperImpl.class.getName());

    @Override
    public Set<Apartment> scrapApartments() {
        LOGGER.log(Level.INFO, "Scrapping Olx.pl in search for apartments.");
        // TODO
        return Collections.emptySet();
    }
}
