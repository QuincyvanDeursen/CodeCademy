package Domain;

import java.util.Date;

public abstract class ContentItem {
    private int ID;
    private String title;
    private Date publicationDate;
    private Status status;

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
