package Domain;

import java.util.Date;

public class Webcast extends ContentItem{
    private String url;
    private int duration;
    private SpeakerWebcast speakerWebcast;
    
    //Constructor
    public Webcast(int ID, String title, Date publicationDate, Status status, String url, int duration, SpeakerWebcast speaker) {
        super(ID, title, publicationDate, status);
        this.url = url;
        this.duration = duration;
        this.speakerWebcast = speaker;
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
