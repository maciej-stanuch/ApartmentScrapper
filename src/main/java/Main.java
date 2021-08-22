import com.google.inject.Guice;
import com.google.inject.Injector;
import scraper.ScraperModule;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(
                new ScraperModule()
        );

        injector.getInstance(App.class).start();
    }
}
