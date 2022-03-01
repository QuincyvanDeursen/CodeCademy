package Domain;

public class Progress {
    private Student student;
    private ContentItem contentItem;
    private int percentage;

    //Constructor
    public Progress(Student student, ContentItem contentItem, int percentage) {
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

    public void setContentItem(ContentItem contentItem) {
        this.contentItem = contentItem;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
