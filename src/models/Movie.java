package models;

public class Movie extends Media {
    private static final long serialVersionUID = 1L;

    private boolean watched;

    public Movie(String title, String director, int releaseYear, String genre) {
        super(title, director, releaseYear, genre);
        this.watched = false;
    }

    public boolean isWatchedFlag() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    @Override
    public void markAsWatched() {
        this.watched = true;
    }

    @Override
    public boolean isWatched() {
        return watched;
    }

    @Override
    public String getMediaType() {
        return "Movie";
    }

    @Override
    public void displayInfo() {
        displayInfo(false);
    }

    @Override
    public void displayInfo(boolean showRating) {
        super.displayInfo(showRating);
        System.out.println("Watched: " + (watched ? "Yes" : "No"));
    }
}
