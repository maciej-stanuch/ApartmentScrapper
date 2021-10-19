package scraper;

import com.google.inject.Singleton;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class OtodomApartmentScrapperImpl implements ApartmentScrapper {

    private final static Logger LOGGER = Logger.getLogger(OtodomApartmentScrapperImpl.class.getName());
    private final static String OTODOM_URL = "https://www.otodom.pl/pl/oferty/wynajem/mieszkanie/wiele-lokalizacji?distanceRadius=0&isPrivateOwner=true&market=ALL&page=1&limit=24&by=LATEST&direction=DESC&locations[0][regionId]=11&locations[0][cityId]=206&locations[0][subregionId]=278&locations[1][regionId]=11&locations[1][cityId]=40&locations[1][subregionId]=439";

    @Override
    public Set<Apartment> scrapApartments() throws IOException {
        LOGGER.log(Level.INFO, "Scrapping Otodom.pl in search for apartments.");
        Document doc = Jsoup.connect(OTODOM_URL).get();
        Element listing = doc.select("[data-cy=frontend.search.listing]").get(1);
        
        Elements listingItems = listing.select("[data-cy=listing-item-link]");

        Set<Apartment> apartments = new HashSet<>();
        for (Element item : listingItems) {
            String listingUrl = item.absUrl("href");
            String listingTitle = Objects.requireNonNull(item.select("[data-cy=listing-item-title]").first()).attr("title");
            Elements locationPriceSize = item.select("p");
            String listingLocation = Objects.requireNonNull(locationPriceSize.get(0).select("span").first()).text();
            String listingPrice = Objects.requireNonNull(locationPriceSize.get(1)).text();
            String listingRooms = Objects.requireNonNull(locationPriceSize.get(2).select("span").get(0)).text();
            String listingArea = Objects.requireNonNull(locationPriceSize.get(2).select("span").get(1)).text();

            Apartment.Builder apartmentBuilder = Apartment.builder();

            apartmentBuilder
                    .setUrl(listingUrl)
                    .setTitle(listingTitle)
                    .setLocation(listingLocation)
                    .setPrice(listingPrice)
                    .setRooms(listingRooms)
                    .setArea(listingArea)
                    .setSource(Apartment.ApartmentSource.OTODOM_PL);

            apartments.add(apartmentBuilder.build());
        }

        return apartments;
    }
}
