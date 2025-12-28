package models;

import java.util.ArrayList;

public class Series extends Media {
    private static final long serialVersionUID = 1L;

    private ArrayList<Episode> episodes;
    private int nbOfSeasons;

    public Series(String title, String director, int releaseYear, String genre, ArrayList<Episode> episodes, int nbOfSeasons) {
        super(title, director, releaseYear, genre);
        if (episodes == null) {
            this.episodes = new ArrayList<Episode>();
        } else {
            this.episodes = episodes;
        }
        setNbOfSeasons(nbOfSeasons);
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ArrayList<Episode> episodes) {
        if (episodes == null) {
            this.episodes = new ArrayList<Episode>();
        } else {
            this.episodes = episodes;
        }
    }

    public int getNbOfSeasons() {
        return nbOfSeasons;
    }

    public void setNbOfSeasons(int nbOfSeasons) {
        if (nbOfSeasons < 0) {
            this.nbOfSeasons = 0;
        } else {
            this.nbOfSeasons = nbOfSeasons;
        }
    }

    public void addEpisode(Episode episode) {
        if (episode != null) {
            episodes.add(episode);
        }
    }

    public int getTotalEpisodes() {
        return episodes.size();
    }

    public int getWatchedEpisodes() {
        int count = 0;
        for (Episode ep : episodes) {
            if (ep != null && ep.isWatched()) {
                count++;
            }
        }
        return count;
    }

    public double getCompletionPercentage() {
        int total = getTotalEpisodes();
        if (total == 0) {
            return 0.0;
        }
        return (getWatchedEpisodes() * 100.0) / total;
    }

    @Override
    public boolean isWatched() {
        int total = getTotalEpisodes();
        return total > 0 && getWatchedEpisodes() == total;
    }

    @Override
    public void markAsWatched() {
        for (Episode ep : episodes) {
            if (ep != null) {
                ep.markAsWatched();
            }
        }
    }

    @Override
    public String getMediaType() {
        return "TV Series";
    }

    public void displayEpisodes() {
        if (episodes.isEmpty()) {
            System.out.println("No episodes added yet.");
            return;
        }
        for (int i = 0; i < episodes.size(); i++) {
            Episode ep = episodes.get(i);
            System.out.print((i + 1) + ") ");
            if (ep == null) {
                System.out.println("(null episode)");
            } else {
                ep.displayEpisodeInfo();
            }
        }
    }

    @Override
    public void displayInfo() {
        displayInfo(false);
    }

    @Override
    public void displayInfo(boolean showRating) {
        super.displayInfo(showRating);
        System.out.println("Seasons: " + nbOfSeasons);
        System.out.println("Episodes watched: " + getWatchedEpisodes() + " / " + getTotalEpisodes());
        System.out.println("Completion: " + String.format("%.1f", getCompletionPercentage()) + "%");
    }
}
