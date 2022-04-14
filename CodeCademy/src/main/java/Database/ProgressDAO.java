package Database;

import Domain.Progress;
import Domain.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProgressDAO {

    //Method to create a progress record in the database
    public boolean createProgressRecord(Progress progress) {
        String query = "INSERT INTO Progress VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, progress.getLocalDate().toString());
            stmt.setInt(2, progress.getContentItem().getID());
            stmt.setString(3, progress.getStudent().getEmail());
            stmt.setInt(4, progress.getPercentage());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally{
            try { if (stmt != null) stmt.close(); } catch (Exception e) {};
            try { if (conn != null) conn.close(); } catch (Exception e) {};
        }
    }


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

}
