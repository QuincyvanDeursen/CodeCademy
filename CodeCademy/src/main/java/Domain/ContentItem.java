package Domain;

import java.util.Date;

public abstract class ContentItem {
    private final int ID;
    private final String title;
    private final Date publicationDate;
    private final Status status;

    //Constructor
    public ContentItem(int ID, String title, Date publicationDate, Status status) {
        this.ID = ID;
        this.title = title;
        this.publicationDate = publicationDate;
        this.status = status;
    }

    //Getters and Setters
    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return this.getTitle();
    }
}
