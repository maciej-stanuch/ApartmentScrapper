package scraper;

import com.google.inject.Singleton;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class OtodomApartmentScrapperImpl implements ApartmentScrapper {

    private final static Logger LOGGER = Logger.getLogger(OtodomApartmentScrapperImpl.class.getName());

    @Override
    public Set<Apartment> scrapApartments() {
        LOGGER.log(Level.INFO, "Scrapping Otodom.pl in search for apartments.");
        // TODO
        return Collections.emptySet();
    }
}
