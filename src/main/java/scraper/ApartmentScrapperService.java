package scraper;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class ApartmentScrapperService {

    private final Set<ApartmentScrapper> scrappers;

    @Inject
    public ApartmentScrapperService(Set<ApartmentScrapper> scrappers) {
        this.scrappers = scrappers;
    }

    public Set<Apartment> getApartments() {
        return getApartments(apartment -> true);
    }

    public Set<Apartment> getApartments(Predicate<Apartment> filter) {
        return scrappers.stream()
                .flatMap(scrapper -> {
                    try {
                        return scrapper.scrapApartments().stream();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Stream.empty();
                    }
                })
                .filter(filter)
                .collect(Collectors.toSet());
    }
}
