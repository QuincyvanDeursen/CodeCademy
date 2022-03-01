package Domain;

import java.util.Date;

public class Enrollment {
    private Student student;
    private Course course;
    private Date registrationDate;

    //Constructor
    public Enrollment(Student student, Course course, Date registrationDate) {
        this.student = student;
        this.course = course;
        this.registrationDate = registrationDate;
    }

    //Getters and Setters
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
