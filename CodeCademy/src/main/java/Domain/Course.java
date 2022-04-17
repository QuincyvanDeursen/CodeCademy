package Domain;

import java.util.ArrayList;

public class Course {
    private String courseName;
    private String topic;
    private Level level;
    private String preface;
    private ArrayList<Course> relatedCourses;
    private ArrayList<ContentItem> contentItems;

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
