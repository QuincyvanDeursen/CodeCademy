package Domain;

import java.util.Date;

public class Webcast extends ContentItem{
    private final String url;
    private final int duration;
    private final SpeakerWebcast speakerWebcast;
    
    //Constructor
    public Webcast(int ID, String title, Date publicationDate, Status status, String url, int duration, SpeakerWebcast speaker) {
        super(ID, title, publicationDate, status);
        this.url = url;
        this.duration = duration;
        this.speakerWebcast = speaker;
    }
}
