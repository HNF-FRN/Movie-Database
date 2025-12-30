package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import models.Media;

public class MediaDatabase implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int SEARCH_TITLE = 1;
    public static final int SEARCH_DIRECTOR = 2;
    public static final int SEARCH_GENRE = 3;
    public static final int SEARCH_ALL = 4;

    private ArrayList<Media> mediaList;

    public MediaDatabase() {
        mediaList = new ArrayList<Media>();
    }

    public ArrayList<Media> getMediaList() {
        return mediaList;
    }

    public void setMediaList(ArrayList<Media> mediaList) {
        if (mediaList == null) {
            this.mediaList = new ArrayList<Media>();
        } else {
            this.mediaList = mediaList;
        }
    }

    public ArrayList<Media> getAllMedia() {
        return new ArrayList<Media>(mediaList);
    }

    public void addMedia(Media media) {
        if (media != null) {
            mediaList.add(media);
        }
    }

    public void addMedia(String title, String director, int releaseYear) {
        Media media = new Media(title, director, releaseYear);
        mediaList.add(media);
    }

    public ArrayList<Media> search(String keyword) {
        return search(keyword, SEARCH_TITLE);
    }

    public ArrayList<Media> search(String keyword, int searchType) {
        ArrayList<Media> results = new ArrayList<Media>();
        if (keyword == null) {
            keyword = "";
        }
        String key = keyword.trim().toLowerCase();

        for (Media media : mediaList) {
            if (media == null) {
                continue;
            }

            String title = media.getTitle() == null ? "" : media.getTitle().toLowerCase();
            String director = media.getDirector() == null ? "" : media.getDirector().toLowerCase();
            String genre = media.getGenre() == null ? "" : media.getGenre().toLowerCase();

            boolean match = false;

            if (searchType == SEARCH_TITLE) {
                match = title.contains(key);
            } else if (searchType == SEARCH_DIRECTOR) {
                match = director.contains(key);
            } else if (searchType == SEARCH_GENRE) {
                match = genre.contains(key);
            } else if (searchType == SEARCH_ALL) {
                match = title.contains(key) || director.contains(key) || genre.contains(key);
            }

            if (match) {
                results.add(media);
            }
        }

        return results;
    }

    public ArrayList<Media> searchByTitle(String title) {
        return search(title, SEARCH_TITLE);
    }

    public ArrayList<Media> searchByDirector(String director) {
        return search(director, SEARCH_DIRECTOR);
    }

    public ArrayList<Media> searchByGenre(String genre) {
        return search(genre, SEARCH_GENRE);
    }

    public ArrayList<Media> getMediaByType(String type) {
        return getMediaByType(mediaList, type);
    }

    public ArrayList<Media> getMediaByType(ArrayList<Media> source, String type) {
        ArrayList<Media> results = new ArrayList<Media>();
        if (source == null) {
            return results;
        }
        if (type == null) {
            results.addAll(source);
            return results;
        }

        String key = type.trim().toLowerCase();

        for (Media media : source) {
            if (media == null || media.getMediaType() == null) {
                continue;
            }
            String t = media.getMediaType().trim().toLowerCase();
            if (t.equals(key)) {
                results.add(media);
            }
        }

        return results;
    }

    public ArrayList<Media> sortByRatingDesc() {
        return sortByRatingDesc(mediaList);
    }

    public ArrayList<Media> sortByRatingDesc(ArrayList<Media> source) {
        ArrayList<Media> sorted = new ArrayList<Media>();
        if (source == null) {
            return sorted;
        }
        sorted.addAll(source);

        for (int i = 0; i < sorted.size() - 1; i++) {
            int bestIndex = i;
            for (int j = i + 1; j < sorted.size(); j++) {
                Media a = sorted.get(bestIndex);
                Media b = sorted.get(j);

                double ra = (a == null) ? -1.0 : a.getRating();
                double rb = (b == null) ? -1.0 : b.getRating();

                if (rb > ra) {
                    bestIndex = j;
                }
            }

            if (bestIndex != i) {
                Media temp = sorted.get(i);
                sorted.set(i, sorted.get(bestIndex));
                sorted.set(bestIndex, temp);
            }
        }

        return sorted;
    }

    public ArrayList<Media> sortByYearDesc() {
        return sortByYearDesc(mediaList);
    }

    public ArrayList<Media> sortByYearDesc(ArrayList<Media> source) {
        ArrayList<Media> sorted = new ArrayList<Media>();
        if (source == null) {
            return sorted;
        }
        sorted.addAll(source);

        for (int i = 0; i < sorted.size() - 1; i++) {
            int bestIndex = i;
            for (int j = i + 1; j < sorted.size(); j++) {
                Media a = sorted.get(bestIndex);
                Media b = sorted.get(j);

                int ya = (a == null) ? -1 : a.getReleaseYear();
                int yb = (b == null) ? -1 : b.getReleaseYear();

                if (yb > ya) {
                    bestIndex = j;
                }
            }

            if (bestIndex != i) {
                Media temp = sorted.get(i);
                sorted.set(i, sorted.get(bestIndex));
                sorted.set(bestIndex, temp);
            }
        }

        return sorted;
    }

    public boolean rateMedia(Media media, double rating) {
        if (media == null) {
            return false;
        }
        media.setRating(rating);
        return true;
    }

    public boolean markAsWatched(Media media) {
        if (media == null) {
            return false;
        }
        media.markAsWatched();
        return true;
    }

    public ArrayList<Media> getWatchedMedia() {
        ArrayList<Media> results = new ArrayList<Media>();
        for (Media media : mediaList) {
            if (media != null && media.isWatched()) {
                results.add(media);
            }
        }
        return results;
    }

    public ArrayList<Media> getUnwatchedMedia() {
        ArrayList<Media> results = new ArrayList<Media>();
        for (Media media : mediaList) {
            if (media != null && !media.isWatched()) {
                results.add(media);
            }
        }
        return results;
    }

    public void displayAllMedia() {
        displayMedia(mediaList);
    }

    public void displayMedia(ArrayList<Media> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("No media to display.");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            Media media = list.get(i);
            System.out.println("--------------------------------------------------");
            System.out.println((i + 1) + ") " + (media == null ? "(null)" : media.getShortDescription()));
            if (media != null) {
                media.displayInfo(true);
            }
        }
        System.out.println("--------------------------------------------------");
    }

    public int getMediaCount() {
        return mediaList.size();
    }

    public double getAverageRating() {
        if (mediaList.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Media media : mediaList) {
            if (media != null) {
                sum += media.getRating();
            }
        }

        return sum / mediaList.size();
    }

    public double getAverageRating(String mediaType) {
        if (mediaType == null) {
            return getAverageRating();
        }

        int count = 0;
        double sum = 0.0;
        String key = mediaType.trim().toLowerCase();

        for (Media media : mediaList) {
            if (media == null || media.getMediaType() == null) {
                continue;
            }
            String t = media.getMediaType().trim().toLowerCase();
            if (t.equals(key)) {
                count++;
                sum += media.getRating();
            }
        }

        if (count == 0) {
            return 0.0;
        }
        return sum / count;
    }

    public int countMovies() {
        int count = 0;
        for (Media media : mediaList) {
            if (media != null && "Movie".equals(media.getMediaType())) {
                count++;
            }
        }
        return count;
    }

    public int countSeries() {
        int count = 0;
        for (Media media : mediaList) {
            if (media != null && "TV Series".equals(media.getMediaType())) {
                count++;
            }
        }
        return count;
    }

    public int countDocumentaries() {
        int count = 0;
        for (Media media : mediaList) {
            if (media != null && "Documentary".equals(media.getMediaType())) {
                count++;
            }
        }
        return count;
    }

    public boolean saveToFile(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return false;
        }

        try {
            File file = new File(filePath);
            File parent = file.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(this);
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static MediaDatabase loadFromFile(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return null;
        }

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
            Object obj = in.readObject();
            in.close();

            if (obj instanceof MediaDatabase) {
                return (MediaDatabase) obj;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
