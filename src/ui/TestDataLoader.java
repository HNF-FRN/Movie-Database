package ui;

import java.util.ArrayList;

import models.Documentary;
import models.Episode;
import models.Movie;
import models.Series;
import services.MediaDatabase;

public class TestDataLoader {

    private static final String SAVE_FILE = "data/media_database.ser";

    public static void main(String[] args) {
        System.out.println("=== Test Data Loader ===");

        MediaDatabase db = new MediaDatabase();

        addSampleMovies(db);
        addSampleSeries(db);
        addSampleDocumentaries(db);

        System.out.println();
        System.out.println("--- Database Preview ---");
        db.displayAllMedia();

        System.out.println();
        System.out.println("--- Saving Database ---");
        boolean saved = db.saveToFile(SAVE_FILE);
        if (saved) {
            System.out.println("Saved successfully to: " + SAVE_FILE);
        } else {
            System.out.println("Save failed.");
        }

        System.out.println("=== Done ===");
    }

    private static void addSampleMovies(MediaDatabase db) {
        System.out.println("Adding Movies...");

        Movie m1 = new Movie("Inception", "Christopher Nolan", 2010, "Sci-Fi");
        Movie m2 = new Movie("The Godfather", "Francis Ford Coppola", 1972, "Crime");
        Movie m3 = new Movie("Batman Begins", "Christopher Nolan", 2005, "Action");

        m1.markAsWatched();

        db.addMedia(m1);
        db.addMedia(m2);
        db.addMedia(m3);
    }

    private static void addSampleSeries(MediaDatabase db) {
        System.out.println("Adding Series...");

        ArrayList<Episode> bbEpisodes = new ArrayList<Episode>();
        bbEpisodes.add(new Episode("Pilot", 58));
        bbEpisodes.add(new Episode("Cat's in the Bag...", 48));
        bbEpisodes.add(new Episode("And the Bag's in the River", 48));

        bbEpisodes.get(0).markAsWatched();

        Series breakingBad = new Series(
                "Breaking Bad",
                "Vince Gilligan",
                2008,
                "Crime Drama",
                bbEpisodes,
                5
        );
        db.addMedia(breakingBad);

        Series invincible = new Series(
                "Invincible",
                "Robert Kirkman",
                2021,
                "Adult Animation",
                new ArrayList<Episode>(),
                3
        );
        db.addMedia(invincible);
    }

    private static void addSampleDocumentaries(MediaDatabase db) {
        System.out.println("Adding Documentaries...");

        Documentary d1 = new Documentary("Planet Earth", "Alastair Fothergill", 2006, "Nature", "Wildlife", true);
        Documentary d2 = new Documentary("Won't You Be My Neighbor?", "Morgan Neville", 2018, "Biography", "Mr. Rogers", true);

        d1.markAsWatched();

        db.addMedia(d1);
        db.addMedia(d2);
    }
}
