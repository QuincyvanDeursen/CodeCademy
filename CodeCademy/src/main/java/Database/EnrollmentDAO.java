package Database;

import Domain.*;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class EnrollmentDAO {

    private final StudentDAO studentDAO = new StudentDAO();
    private final CourseDAO courseDAO = new CourseDAO();
    private final ProgressDAO progressDAO = new ProgressDAO();

//    Returns an Arraylist filled with Enrollment objects by sending a query to the database and make the objects by the pulled records.
    public ArrayList<Enrollment> getEnrollmentList() {
        ArrayList<Enrollment> enrollmentList = new ArrayList<>();
        String query = "select Email, CourseName, RegistrationDate from Enrollment";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Enrollment enrollment = new Enrollment(
                        studentDAO.readStudent(rs.getString("Email")),
                        courseDAO.readCourse(rs.getString("CourseName")),
                        rs.getDate("RegistrationDate").toLocalDate()
                );
                enrollmentList.add(enrollment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
            }
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
            }
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
            }
        }
        return enrollmentList;
    }

//    Adds a record into the Enrollment table with the given values.
    public boolean addRecord(String course, String mail) {
        String query = "INSERT INTO Enrollment VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        Enrollment enrollment = new Enrollment(
                studentDAO.readStudent(mail),
                courseDAO.readCourse(course),
                LocalDate.now()
        );

        try {
            //inserting enrollment record
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, enrollment.getRegistrationDate().toString());
            stmt.setInt(2, 0);
            stmt.setString(3, course);
            stmt.setString(4, mail);
            stmt.executeUpdate();

            //inserting progress records for the corresponding enrollment with 0% progress.
            for (ContentItem c :enrollment.getCourse().getContentItems()) {
                progressDAO.createProgressRecord(
                        new Progress(enrollment.getRegistrationDate(), enrollment.getStudent(), c, 0));

            }


            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
            }
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
            }
        }
    }

//    Deletes a record from the Enrollment table with the given object.
    public boolean deleteRecord(Enrollment enrollment) {
        String query = "DELETE FROM Enrollment WHERE Email = ? AND CourseName = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, enrollment.getStudent().getEmail());
            stmt.setString(2, enrollment.getCourse().getCourseName());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
            }
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
            }
        }
        return false;
    }

//    Updates a record with the given object, with the new values.
    public boolean updateRecord(Enrollment enrollment, String cellMail, String cellCourse) {
        String query = "UPDATE Enrollment SET Email = ? , CourseName = ? WHERE Email = ? AND CourseName = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, enrollment.getStudent().getEmail());
            stmt.setString(2, enrollment.getCourse().getCourseName());
            stmt.setString(3, cellMail);
            stmt.setString(4, cellCourse);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
            }
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
            }
        }
        return false;
    }

    //method below returns each course a given student is enrolled for.
    //This method is used to show the courses a student is enrolled for in a dropdown list.
    public ArrayList<Course> getCoursesOfEnrolledStudent(Student student) {
        String query = "SELECT DISTINCT CourseName FROM Enrollment WHERE Email = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            ArrayList<Course> courses = new ArrayList<>();
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1,student.getEmail());
            rs = stmt.executeQuery();
            while (rs.next()) {
                Course course = courseDAO.readCourse(rs.getString(1));
                courses.add(course);
            }
            return courses;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return null;
    }

    //method below returns a list of distinct emails from enrolled students.
    //This method is used to show the enrolled students in a dropdown list.
    public ArrayList<Student> getDistinctEnrolledStudents() {
        String query = "SELECT DISTINCT Email FROM Enrollment";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            ArrayList<Student> emailsOfEnrolledStudents = new ArrayList<>();
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()) {
                emailsOfEnrolledStudents.add( studentDAO.readStudent(rs.getString(1)));
            }
            return emailsOfEnrolledStudents;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return null;
    }

    //For a given course show how many students completed the course. (view8)
    //This method returns the amount of enrollments for which a certificate has been as
    public int getAmountOfCourseCompleted(Course course) {
        String query = "SELECT COUNT(CertificateID) AS Completed FROM Enrollment WHERE CertificateID = 1 AND CourseName = ? GROUP BY CourseName";
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        int total = -1;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, course.getCourseName());
            rs = stmt.executeQuery();

            if (rs.next()) {
                total = rs.getInt(1);
            }
            return  total;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return total;
    }

    //This method retrieves the course names which have enrolments (view 8).
    public ArrayList<Course> getDistinctEnrolledCourses() {
        String query = "SELECT DISTINCT CourseName FROM Enrollment";
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            ArrayList<Course> courseList = new ArrayList<>();
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Course course = courseDAO.readCourse(rs.getString(1));
                courseList.add(course);
            }
            return courseList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return null;
    }
}
