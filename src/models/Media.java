package models;

import java.io.Serializable;

public class Media implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String director;
    private int releaseYear;
    private String genre;
    private double rating;

    public Media(String title, String director, int releaseYear) {
        this(title, director, releaseYear, "Unknown");
    }

    public Media(String title, String director, int releaseYear, String genre) {
        setTitle(title);
        setDirector(director);
        setReleaseYear(releaseYear);
        setGenre(genre);
        this.rating = 0.0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null) {
            this.title = "";
        } else {
            this.title = title.trim();
        }
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        if (director == null) {
            this.director = "";
        } else {
            this.director = director.trim();
        }
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        if (releaseYear < 0) {
            this.releaseYear = 0;
        } else {
            this.releaseYear = releaseYear;
        }
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        if (genre == null || genre.trim().isEmpty()) {
            this.genre = "Unknown";
        } else {
            this.genre = genre.trim();
        }
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        if (rating < 0.0) {
            this.rating = 0.0;
        } else if (rating > 10.0) {
            this.rating = 10.0;
        } else {
            this.rating = rating;
        }
    }

    public void markAsWatched() {
        // Default: not all media types have a direct watched flag.
    }

    public boolean isWatched() {
        return false;
    }

    public String getMediaType() {
        return "Media";
    }

    public void displayInfo() {
        displayInfo(false);
    }

    public void displayInfo(boolean showRating) {
        System.out.println("Type: " + getMediaType());
        System.out.println("Title: " + title);
        System.out.println("Director: " + director);
        System.out.println("Release Year: " + releaseYear);
        System.out.println("Genre: " + genre);
        if (showRating) {
            System.out.println("Rating: " + rating + " / 10.0");
        }
    }

    public String getShortDescription() {
        return "[" + getMediaType() + "] " + title + " (" + releaseYear + ") - " + director;
    }
}
