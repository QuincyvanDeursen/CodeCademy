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

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.getTitle();
    }
}
