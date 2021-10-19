package scraper;

import com.google.inject.Singleton;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class TrojmiastoApartmentScrapperImpl implements ApartmentScrapper {

    private final static Logger LOGGER = Logger.getLogger(TrojmiastoApartmentScrapperImpl.class.getName());
    private final static String TROJMIASTO_URL = "https://ogloszenia.trojmiasto.pl/nieruchomosci-mam-do-wynajecia/mieszkanie/f1i,1_3,o1,1.html";

    @Override
    public Set<Apartment> scrapApartments() throws IOException {
        LOGGER.log(Level.INFO, "Scrapping Trojmiasto.pl in search for apartments.");
        Document doc = Jsoup.connect(TROJMIASTO_URL).get();

        Elements listItems = doc.select("div.list--item--withPrice");

        Set<Apartment> apartments = new HashSet<>();
        for (Element item : listItems) {
            Element listingHeader = item.selectFirst("a.list__item__content__title__name");
            Element listingSubtitle = item.selectFirst("p.list__item__content__subtitle");
            Element listingAreaIcon = item.selectFirst("li.details--icons--element--powierzchnia");
            Element listingRoomsIcon = item.selectFirst("li.details--icons--element--l_pokoi");
            Element listingPriceInfo = item.selectFirst("p.list__item__price__value");

            Apartment.Builder apartmentBuilder = Apartment.builder();

            if (listingHeader != null) {
                apartmentBuilder
                        .setTitle(listingHeader.attr("title"))
                        .setUrl(listingHeader.absUrl("href"));
            }
            if (listingSubtitle != null) {
                apartmentBuilder.setLocation(listingSubtitle.text());
            }
            if (listingAreaIcon != null) {
                String listingArea = listingAreaIcon.select("p.list__item__details__icons__element__desc").text();
                apartmentBuilder.setArea(listingArea);
            }
            if (listingRoomsIcon != null) {
                String listingRooms = listingRoomsIcon.select("p.list__item__details__icons__element__desc").text();
                apartmentBuilder.setRooms(listingRooms);
            }
            if (listingPriceInfo != null) {
                apartmentBuilder.setPrice(listingPriceInfo.text());
            }
            apartmentBuilder.setSource(Apartment.ApartmentSource.TROJMIASTO_PL);

            apartments.add(apartmentBuilder.build());
        }

        LOGGER.log(Level.INFO, "Scrapped " + apartments.size() + " apartments from Trojmiasto.pl");
        return apartments;
    }
}
