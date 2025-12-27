package models;

public class Documentary extends Media {
    private static final long serialVersionUID = 1L;

    private String topic;
    private boolean educational;
    private boolean watched;

    public Documentary(String title, String director, int releaseYear, String genre, String topic, boolean educational) {
        super(title, director, releaseYear, genre);
        setTopic(topic);
        this.educational = educational;
        this.watched = false;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        if (topic == null) {
            this.topic = "";
        } else {
            this.topic = topic.trim();
        }
    }

    public boolean isEducational() {
        return educational;
    }

    public void setEducational(boolean educational) {
        this.educational = educational;
    }

    public boolean isWatchedFlag() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public String getEducationalValue() {
        return educational ? "Educational" : "Not Educational";
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
        return "Documentary";
    }

    @Override
    public void displayInfo() {
        displayInfo(false);
    }

    @Override
    public void displayInfo(boolean showRating) {
        super.displayInfo(showRating);
        System.out.println("Topic: " + topic);
        System.out.println("Educational: " + (educational ? "Yes" : "No"));
        System.out.println("Watched: " + (watched ? "Yes" : "No"));
    }
}
