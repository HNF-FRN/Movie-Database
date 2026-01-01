package ui;

import java.util.ArrayList;
import java.util.Scanner;

import models.Documentary;
import models.Episode;
import models.Media;
import models.Movie;
import models.Series;
import services.MediaDatabase;

public class Application {
    private static final String SAVE_FILE = "data/media_database.ser";

    private static final int FILTER_ALL = 1;
    private static final int FILTER_MOVIE = 2;
    private static final int FILTER_SERIES = 3;
    private static final int FILTER_DOCUMENTARY = 4;

    private static final int SORT_NONE = 1;
    private static final int SORT_RATING_DESC = 2;
    private static final int SORT_YEAR_DESC = 3;

    private MediaDatabase database;
    private Scanner scanner;
    private boolean isRunning;

    public Application() {
        database = new MediaDatabase();
        scanner = new Scanner(System.in);
        isRunning = true;
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    private void run() {
        while (isRunning) {
            printMenu();
            int choice = readIntInRange("Choose an option: ", 0, 12);

            switch (choice) {
                case 1:
                    addMovie();
                    break;
                case 2:
                    addSeries();
                    break;
                case 3:
                    addDocumentary();
                    break;
                case 4:
                    addEpisodeToSeries();
                    break;
                case 5:
                    searchMedia();
                    break;
                case 6:
                    rateMedia();
                    break;
                case 7:
                    markWatched();
                    break;
                case 8:
                    displayAllMediaEnhanced();
                    break;
                case 9:
                    displayWatchedUnwatched();
                    break;
                case 10:
                    showStatistics();
                    break;
                case 11:
                    saveDatabase();
                    break;
                case 12:
                    loadDatabase();
                    break;
                case 0:
                    exit();
                    break;
                default:
                    System.out.println("Invalid option.");
            }

            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("==============================================");
        System.out.println("            MEDIA DATABASE - MENU             ");
        System.out.println("==============================================");
        System.out.println("1)  Add Movie");
        System.out.println("2)  Add Series");
        System.out.println("3)  Add Documentary");
        System.out.println("4)  Add Episode to Series");
        System.out.println("5)  Search Media");
        System.out.println("6)  Rate Media");
        System.out.println("7)  Mark Watched");
        System.out.println("8)  Display All Media");
        System.out.println("9)  Display Watched / Unwatched");
        System.out.println("10) Statistics");
        System.out.println("11) Save");
        System.out.println("12) Load");
        System.out.println("0)  Exit");
        System.out.println("==============================================");
    }

    private void addMovie() {
        System.out.println("--- Add Movie ---");
        String title = readNonEmptyLine("Title: ");
        String director = readNonEmptyLine("Director: ");
        int year = readIntInRange("Release year: ", 0, 3000);
        String genre = readNonEmptyLine("Genre: ");

        Movie movie = new Movie(title, director, year, genre);

        boolean watched = readYesNo("Watched? (y/n): ");
        if (watched) {
            movie.markAsWatched();
        }

        database.addMedia(movie);
        System.out.println("Movie added successfully.");
    }

    private void addDocumentary() {
        System.out.println("--- Add Documentary ---");
        String title = readNonEmptyLine("Title: ");
        String director = readNonEmptyLine("Director: ");
        int year = readIntInRange("Release year: ", 0, 3000);
        String genre = readNonEmptyLine("Genre: ");
        String topic = readNonEmptyLine("Topic: ");
        boolean educational = readYesNo("Is it educational? (y/n): ");

        Documentary documentary = new Documentary(title, director, year, genre, topic, educational);

        boolean watched = readYesNo("Watched? (y/n): ");
        if (watched) {
            documentary.markAsWatched();
        }

        database.addMedia(documentary);
        System.out.println("Documentary added successfully.");
    }

    private void addSeries() {
        System.out.println("--- Add Series ---");
        String title = readNonEmptyLine("Title: ");
        String director = readNonEmptyLine("Director: ");
        int year = readIntInRange("Release year: ", 0, 3000);
        String genre = readNonEmptyLine("Genre: ");
        int seasons = readIntInRange("Number of seasons: ", 0, 100);

        int countEpisodes = readIntInRange("How many episodes to add now? ", 0, 500);

        ArrayList<Episode> episodes = new ArrayList<Episode>();
        for (int i = 1; i <= countEpisodes; i++) {
            System.out.println("Episode " + i + ":");
            Episode ep = createEpisodeFromInput();
            episodes.add(ep);
        }

        Series series = new Series(title, director, year, genre, episodes, seasons);
        database.addMedia(series);

        System.out.println("Series added successfully.");
    }

    private Episode createEpisodeFromInput() {
        String epTitle = readNonEmptyLine("  Episode title: ");
        int duration = readIntInRange("  Duration (minutes): ", 0, 10000);

        Episode ep = new Episode(epTitle, duration);

        boolean watched = readYesNo("  Watched? (y/n): ");
        if (watched) {
            ep.markAsWatched();
        }

        return ep;
    }

    private void addEpisodeToSeries() {
        System.out.println("--- Add Episode to Series ---");
        Series series = selectSeriesByTitle();
        if (series == null) {
            return;
        }

        Episode ep = createEpisodeFromInput();
        series.addEpisode(ep);

        System.out.println("Episode added to series: " + series.getTitle());
    }

    private void searchMedia() {
        System.out.println("--- Search Media ---");

        int searchType = readSearchType();
        String keyword = readNonEmptyLine("Enter keyword: ");

        ArrayList<Media> results = database.search(keyword, searchType);

        if (results.isEmpty()) {
            System.out.println("No results found.");
            return;
        }

        results = applyTypeFilterStep(results);
        results = applySortStep(results);

        displayMediaList(results);

        if (results.isEmpty()) {
            return;
        }

        int choice = readIntInRange("Select an item to view details (0 to cancel): ", 0, results.size());
        if (choice == 0) {
            return;
        }

        Media selected = results.get(choice - 1);
        System.out.println("--------------- DETAILS ---------------");
        selected.displayInfo(true);

        if (selected instanceof Series) {
            Series s = (Series) selected;
            System.out.println("--------------- EPISODES --------------");
            s.displayEpisodes();
        }
        System.out.println("--------------------------------------");
    }

    private void rateMedia() {
        System.out.println("--- Rate Media ---");
        Media selected = selectMedia("Select media to rate");
        if (selected == null) {
            return;
        }

        double rating = readDoubleInRange("Enter rating (0.0 - 10.0): ", 0.0, 10.0);
        database.rateMedia(selected, rating);

        System.out.println("Rating updated.");
    }

    private void markWatched() {
        System.out.println("--- Mark Watched ---");
        Media selected = selectMedia("Select media to mark as watched");
        if (selected == null) {
            return;
        }

        if (selected instanceof Series) {
            markSeriesWatched((Series) selected);
        } else {
            database.markAsWatched(selected);
            System.out.println("Marked as watched.");
        }
    }

    private void markSeriesWatched(Series series) {
        if (series.getTotalEpisodes() == 0) {
            System.out.println("This series has no episodes yet. Add episodes first.");
            return;
        }

        System.out.println("1) Mark one episode as watched");
        System.out.println("2) Mark ALL episodes as watched");
        System.out.println("0) Cancel");
        int option = readIntInRange("Choose: ", 0, 2);

        if (option == 0) {
            return;
        }

        if (option == 2) {
            database.markAsWatched(series);
            System.out.println("All episodes marked as watched.");
            return;
        }

        System.out.println("Episodes:");
        series.displayEpisodes();
        int index = readIntInRange("Select episode (0 to cancel): ", 0, series.getEpisodes().size());
        if (index == 0) {
            return;
        }

        Episode ep = series.getEpisodes().get(index - 1);
        ep.markAsWatched();
        System.out.println("Episode marked as watched.");
    }

    private void displayAllMediaEnhanced() {
        System.out.println("--- Display All Media ---");

        ArrayList<Media> list = database.getAllMedia();
        if (list.isEmpty()) {
            System.out.println("No media in the database.");
            return;
        }

        list = applyTypeFilterStep(list);
        list = applySortStep(list);

        database.displayMedia(list);
    }

    private void displayWatchedUnwatched() {
        System.out.println("--- Display Watched / Unwatched ---");
        System.out.println("1) Watched");
        System.out.println("2) Unwatched");
        int option = readIntInRange("Choose: ", 1, 2);

        ArrayList<Media> list;
        if (option == 1) {
            list = database.getWatchedMedia();
        } else {
            list = database.getUnwatchedMedia();
        }

        if (list.isEmpty()) {
            System.out.println("No media to display.");
            return;
        }

        database.displayMedia(list);
    }

    private void showStatistics() {
        System.out.println("--- Statistics ---");

        int total = database.getMediaCount();
        int movies = database.countMovies();
        int series = database.countSeries();
        int documentaries = database.countDocumentaries();

        System.out.println("Total media items: " + total);
        System.out.println("Movies: " + movies);
        System.out.println("TV Series: " + series);
        System.out.println("Documentaries: " + documentaries);

        System.out.println();
        System.out.println("Average rating (All): " + formatOneDecimal(database.getAverageRating()) + " / 10.0");
        System.out.println("Average rating (Movies): " + formatOneDecimal(database.getAverageRating("Movie")) + " / 10.0");
        System.out.println("Average rating (TV Series): " + formatOneDecimal(database.getAverageRating("TV Series")) + " / 10.0");
        System.out.println("Average rating (Documentaries): " + formatOneDecimal(database.getAverageRating("Documentary")) + " / 10.0");

        System.out.println();
        System.out.println("--- Series Completion Percentages ---");
        ArrayList<Media> all = database.getAllMedia();

        int seriesCount = 0;
        double sumCompletion = 0.0;

        for (Media media : all) {
            if (media instanceof Series) {
                Series s = (Series) media;
                seriesCount++;

                double pct = s.getCompletionPercentage();
                sumCompletion += pct;

                System.out.println("- " + s.getTitle() + ": " + s.getWatchedEpisodes() + " / " + s.getTotalEpisodes()
                        + " (" + formatOneDecimal(pct) + "%)");
            }
        }

        if (seriesCount == 0) {
            System.out.println("No series in the database.");
        } else {
            double avgCompletion = sumCompletion / seriesCount;
            System.out.println("Average series completion: " + formatOneDecimal(avgCompletion) + "%");
        }
    }

    private void saveDatabase() {
        boolean ok = database.saveToFile(SAVE_FILE);
        if (ok) {
            System.out.println("Database saved to: " + SAVE_FILE);
        } else {
            System.out.println("Save failed.");
        }
    }

    private void loadDatabase() {
        MediaDatabase loaded = MediaDatabase.loadFromFile(SAVE_FILE);
        if (loaded != null) {
            database = loaded;
            System.out.println("Database loaded from: " + SAVE_FILE);
        } else {
            System.out.println("Load failed. Make sure the save file exists.");
        }
    }

    private void exit() {
        isRunning = false;
        System.out.println("Goodbye!");
        scanner.close();
    }

    private ArrayList<Media> applyTypeFilterStep(ArrayList<Media> currentList) {
        System.out.println();
        System.out.println("Filter by type:");
        System.out.println("1) All");
        System.out.println("2) Movies only");
        System.out.println("3) TV Series only");
        System.out.println("4) Documentaries only");

        int choice = readIntInRange("Choose: ", 1, 4);

        if (choice == FILTER_ALL) {
            return currentList;
        }

        String type = null;
        if (choice == FILTER_MOVIE) {
            type = "Movie";
        } else if (choice == FILTER_SERIES) {
            type = "TV Series";
        } else if (choice == FILTER_DOCUMENTARY) {
            type = "Documentary";
        }

        return database.getMediaByType(currentList, type);
    }

    private ArrayList<Media> applySortStep(ArrayList<Media> currentList) {
        System.out.println();
        System.out.println("Sort:");
        System.out.println("1) None");
        System.out.println("2) By rating (highest first)");
        System.out.println("3) By year (newest first)");

        int choice = readIntInRange("Choose: ", 1, 3);

        if (choice == SORT_NONE) {
            return currentList;
        }
        if (choice == SORT_RATING_DESC) {
            return database.sortByRatingDesc(currentList);
        }
        return database.sortByYearDesc(currentList);
    }

    private int readSearchType() {
        System.out.println("Search by:");
        System.out.println("1) Title");
        System.out.println("2) Director");
        System.out.println("3) Genre");
        System.out.println("4) All");
        return readIntInRange("Choose: ", 1, 4);
    }

    private Media selectMedia(String header) {
        if (database.getMediaCount() == 0) {
            System.out.println("The database is empty.");
            return null;
        }

        System.out.println(header);
        int searchType = readSearchType();
        String keyword = readNonEmptyLine("Enter keyword: ");

        ArrayList<Media> results = database.search(keyword, searchType);
        if (results.isEmpty()) {
            System.out.println("No results found.");
            return null;
        }

        results = applyTypeFilterStep(results);
        results = applySortStep(results);

        displayMediaList(results);

        if (results.isEmpty()) {
            return null;
        }

        int choice = readIntInRange("Select an item (0 to cancel): ", 0, results.size());
        if (choice == 0) {
            return null;
        }

        return results.get(choice - 1);
    }

    private Series selectSeriesByTitle() {
        if (database.getMediaCount() == 0) {
            System.out.println("The database is empty.");
            return null;
        }

        String keyword = readNonEmptyLine("Enter series title keyword: ");
        ArrayList<Media> results = database.searchByTitle(keyword);

        ArrayList<Series> seriesResults = new ArrayList<Series>();
        for (Media media : results) {
            if (media instanceof Series) {
                seriesResults.add((Series) media);
            }
        }

        if (seriesResults.isEmpty()) {
            System.out.println("No series found for this keyword.");
            return null;
        }

        for (int i = 0; i < seriesResults.size(); i++) {
            Series s = seriesResults.get(i);
            System.out.println((i + 1) + ") " + s.getShortDescription());
        }

        int choice = readIntInRange("Select a series (0 to cancel): ", 0, seriesResults.size());
        if (choice == 0) {
            return null;
        }

        return seriesResults.get(choice - 1);
    }

    private void displayMediaList(ArrayList<Media> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("No results found.");
            return;
        }

        System.out.println("Results:");
        for (int i = 0; i < list.size(); i++) {
            Media media = list.get(i);
            System.out.println((i + 1) + ") " + (media == null ? "(null)" : media.getShortDescription()));
        }
    }

    private String readNonEmptyLine(String prompt) {
        String value = "";
        while (value.isEmpty()) {
            System.out.print(prompt);
            value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                System.out.println("Please enter a value.");
            }
        }
        return value;
    }

    private int readIntInRange(String prompt, int min, int max) {
        int value;

        while (true) {
            System.out.print(prompt);

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
                continue;
            }

            value = scanner.nextInt();
            scanner.nextLine();

            if (value < min || value > max) {
                System.out.println("Please enter a value between " + min + " and " + max + ".");
                continue;
            }

            return value;
        }
    }

    private double readDoubleInRange(String prompt, double min, double max) {
        double value;

        while (true) {
            System.out.print(prompt);

            if (!scanner.hasNextDouble()) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
                continue;
            }

            value = scanner.nextDouble();
            scanner.nextLine();

            if (value < min || value > max) {
                System.out.println("Please enter a value between " + min + " and " + max + ".");
                continue;
            }

            return value;
        }
    }

    private boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y") || input.equals("yes")) {
                return true;
            }
            if (input.equals("n") || input.equals("no")) {
                return false;
            }

            System.out.println("Please answer with y/n.");
        }
    }

    private String formatOneDecimal(double value) {
        return String.format("%.1f", value);
    }
}
