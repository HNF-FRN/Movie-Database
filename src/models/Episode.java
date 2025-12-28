package models;

import java.io.Serializable;

public class Episode implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private int duration; // minutes
    private boolean watched;

    public Episode(String title, int duration) {
        setTitle(title);
        setDuration(duration);
        this.watched = false;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (duration < 0) {
            this.duration = 0;
        } else {
            this.duration = duration;
        }
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public void markAsWatched() {
        this.watched = true;
    }

    public void displayEpisodeInfo() {
        System.out.println(title + " (" + duration + " min) - Watched: " + (watched ? "Yes" : "No"));
    }

    public String getShortDescription() {
        return title + " (" + duration + " min) - " + (watched ? "Watched" : "Unwatched");
    }
}
