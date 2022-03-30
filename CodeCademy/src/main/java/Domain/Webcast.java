package Domain;

import java.util.Date;

public class Webcast extends ContentItem{
    private String url;
    private int duration;
    
    //Constructor
    public Webcast(int ID, String title, Date publicationDate, Status status, String url, int duration) {
        super(ID, title, publicationDate, status);
        this.url = url;
        this.duration = duration;
    }

    //Getters and settesr
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
