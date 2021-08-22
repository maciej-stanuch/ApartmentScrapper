package scraper;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class ScraperModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder<ApartmentScrapper> binder = Multibinder.newSetBinder(binder(), ApartmentScrapper.class);
        binder.addBinding().to(TrojmiastoApartmentScrapperImpl.class);
        binder.addBinding().to(OlxApartmentScrapperImpl.class);
        binder.addBinding().to(OtodomApartmentScrapperImpl.class);
    }
}
