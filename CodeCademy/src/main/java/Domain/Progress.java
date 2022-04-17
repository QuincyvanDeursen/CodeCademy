package Domain;

import java.time.LocalDate;

public class Progress {
    private LocalDate localDate;
    private Student student;
    private ContentItem contentItem;
    private int percentage;

    //Constructor
    public Progress(LocalDate localDate, Student student, ContentItem contentItem, int percentage) {
        this.localDate = localDate;
        this.student = student;
        this.contentItem = contentItem;
        this.percentage = percentage;
    }

    //Getters and setters
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ContentItem getContentItem() {
        return contentItem;
    }

    public int getPercentage() {
        return percentage;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
}
