package Domain;

import java.util.ArrayList;

public class Course {
    private final String courseName;
    private final String topic;
    private final Level level;
    private final String preface;
    private final ArrayList<Course> relatedCourses;
    private final ArrayList<ContentItem> contentItems;

    //Constructor
    public Course(String courseName, String topic, Level level, String preface, ArrayList<Course> relatedCourses, ArrayList<ContentItem> contentItems) {
        this.courseName = courseName;
        this.topic = topic;
        this.level = level;
        this.preface = preface;
        this.relatedCourses = relatedCourses;
        this.contentItems = contentItems;
    }

    //Setters and Getters
    public String getCourseName() {
        return courseName;
    }

    public ArrayList<ContentItem> getContentItems() {
        return contentItems;
    }

    @Override
    public String toString() {
        return this.getCourseName();
    }
}
