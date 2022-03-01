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

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getPreface() {
        return preface;
    }

    public void setPreface(String preface) {
        this.preface = preface;
    }

    public ArrayList<Course> getRelatedCourses() {
        return relatedCourses;
    }

    public void setRelatedCourses(ArrayList<Course> relatedCourses) {
        this.relatedCourses = relatedCourses;
    }

    public ArrayList<ContentItem> getContentItems() {
        return contentItems;
    }

    public void setContentItems(ArrayList<ContentItem> contentItems) {
        this.contentItems = contentItems;
    }
}
