package Database;


import java.sql.*;
import java.util.ArrayList;


public class StatisticsDAO {
    //For a selected course give the average progression in percentage  of the total length for each module of all students. (view2)
    public ArrayList<String> getAverageProgressionOfAllModulesFromCourse(String course){
        String query = "SELECT ContentItem.ModuleTitle, AVG(Progress.Progression) AS Average FROM Progress JOIN ContentItem ON ContentItem.ContentID = Progress.ContentID JOIN CourseContent ON CourseContent.ContentID = ContentItem.ContentID WHERE ContentItem.ModuleTitle IS NOT null AND CourseContent.CourseName = ? GROUP BY ContentItem.ModuleTitle, CourseContent.CourseName";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            ArrayList<String> moduleProgression = new ArrayList<>();
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, course);
            rs = stmt.executeQuery();
            while (rs.next()) {
                moduleProgression.add(rs.getString(1) +": " + rs.getString(2));
            }
            return moduleProgression;
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

    //method to retrieve all courses. This method is used to show all courses in a dropdownlist for the 'StatisticsDAO.getAverageProgressionOfAllModulesFromCourse' method.
    public ArrayList<String> getCourseNames(){
        String query = "SELECT CourseName FROM Course";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            ArrayList<String> courses = new ArrayList<>();
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(rs.getString(1));
            }
            return courses;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            try { if (rs != null) rs.close(); } catch (Exception e) {};
            try { if (stmt != null) stmt.close(); } catch (Exception e) {};
            try { if (conn != null) conn.close(); } catch (Exception e) {};
        }
        return null;
    }


    //For a given student and course, give the corresponding progress of each module. (view3)
    //Method below returns a HashMap that contains the name of the module and the progression of the corresponding course and student.
    public ArrayList<String> getModuleProgression(String email, String courseName) {
        String query = "SELECT ModuleTitle, Progression FROM CourseContent Join ContentItem ON ContentItem.ContentID = CourseContent.ContentID Join Progress ON Progress.ContentID = ContentItem.ContentID WHERE Email = ? AND CourseName = ? AND ModuleTitle IS NOT NULL";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            ArrayList<String> moduleProgression = new ArrayList<>();
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, courseName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                moduleProgression.add(rs.getString(1) +": " + rs.getString(2));
            }
            return moduleProgression;
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


    //method below returns each course a given student is enrolled for.
    //This method is used to show the courses a student is enrolled for in a dropdown list. The selected course is used for the "StatisticsDAO.getModuleProgression" method.
    public ArrayList<String> getCoursesFromEnrolledStudent(String email) {
        String query = "SELECT DISTINCT CourseName FROM Enrollment WHERE Email = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            ArrayList<String> progression = new ArrayList<>();
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1,email);
            rs = stmt.executeQuery();
            while (rs.next()) {
                progression.add(rs.getString(1));
            }
            return progression;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            try { if (rs != null) rs.close(); } catch (Exception e) {};
            try { if (stmt != null) stmt.close(); } catch (Exception e) {};
            try { if (conn != null) conn.close(); } catch (Exception e) {};
        }
        return null;
    }

    //method below returns a list of emails from enrolled students.
    //This method is used to show the enrolled students in a dropdown list. The selected student is used for the "StatisticsDAO.getModuleProgression" method.
    public ArrayList<String> getEnrolledAccount() {
        String query = "SELECT DISTINCT Email FROM Enrollment";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            ArrayList<String> emailsOfEnrolledStudents = new ArrayList<>();
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()) {
                emailsOfEnrolledStudents.add(rs.getString(1));
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


    //Get top 3 most viewed webcasts (view5)
    public ArrayList<String> getTopThreeWebcast() {
        ArrayList<String> top3Webcast = new ArrayList<>();
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        String query = "SELECT TOP 3 Webcast.Title, SUM(Progression) AS totalPercentageWatched From Progress JOIN ContentItem ON ContentItem.ContentID = Progress.ContentID JOIN Webcast ON ContentItem.WebcastTitle = Webcast.Title WHERE ContentItem.WebcastTitle IS NOT NULL GROUP BY Webcast.Title ORDER BY  totalPercentageWatched DESC";
        try {
             conn = DBConnection.getConnection();
             stmt = conn.prepareStatement(query);
             rs = stmt.executeQuery();
            while (rs.next()) {
                top3Webcast.add(rs.getString(1));
            }
            return top3Webcast;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return null;
    }

    //For a given course show how many students completed the course. (view8)
    //This method returns the amount of enrollments for which a certificate has been as
    public int getAmountOfCourseCompleted(String courseName) {
        String query = "SELECT COUNT(CertificateID) AS Completed FROM Enrollment WHERE CertificateID IS NOT NULL AND CourseName = ? GROUP BY CourseName";
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        int total = -1;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, courseName);
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

    //This method retrieves the course names which have enrolments.
    //This method is used for a dropdown list to select a course for the "getAmountOfCourseCompleted" method (view 8).
    public ArrayList<String> getCourses() {
        String query = "SELECT DISTINCT CourseName FROM Enrollment";
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            ArrayList<String> courseList = new ArrayList<>();
             conn = DBConnection.getConnection();
             stmt = conn.prepareStatement(query);
             rs = stmt.executeQuery();
            while (rs.next()) {
                courseList.add(rs.getString(1));
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
