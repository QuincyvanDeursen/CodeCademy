package Database;

import Domain.*;
import Domain.Module;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ContentItemDAO {
    private WebcastSpeakerDAO  webcastSpeakerDAO = new WebcastSpeakerDAO();
    private ModuleContactPersonDAO moduleContactPersonDAO = new ModuleContactPersonDAO();


    // Retrieve a list of modules for a given course
    public ArrayList<Module> getModulesForCourse(String courseTitle) {
        // Query to retrieve all content items for a course
        String query = "Select CourseContent.ContentID, Module.Title, ContentItem.PublicationDate, ContentItem.[Status], Module.[Version], Module.SerialNumber, Module.EmailContact From CourseContent JOIN ContentItem ON CourseContent.ContentID = ContentItem.ContentID JOIN Module ON ContentItem.ModuleTitle = Module.Title WHERE CourseContent.CourseName = ?";
        // Create an arraylist to store the retrieved data
        ArrayList<Module> modules = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, courseTitle);
            rs = stmt.executeQuery();
            // Loop through the result set
            while (rs.next()) {
                modules.add(
                        new Module(
                                rs.getInt("ContentID"),
                                rs.getString("Title"),
                                rs.getDate("PublicationDate"),
                                Status.returnEnum("Status"),
                                rs.getDouble("Version"),
                                rs.getInt("SerialNumber"),
                                moduleContactPersonDAO.getModuleContactPerson(rs.getString("EmailContact"))
                        ));
            }
            return modules;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }

        // Return nothing on error.
        return null;
    }

    // Retrieve a list of webcasts for a given course
    public ArrayList<Webcast> getWebcastsForCourse(String courseName) {
        // Query to retrieve all webcasts for a course
        String query = "Select CourseContent.ContentID, Webcast.Title, ContentItem.PublicationDate, ContentItem.[Status], Webcast.[URL], Webcast.Duration, Webcast.SpeakerID From CourseContent JOIN ContentItem ON CourseContent.ContentID = ContentItem.ContentID JOIN Webcast ON ContentItem.WebcastTitle = Webcast.Title WHERE CourseContent.CourseName = ?";

        // Create an arraylist to store the retrieved data
        ArrayList<Webcast> webcasts = new ArrayList<>();

        // Create a prepared statement to prevent SQL injections
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;


        try{
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, courseName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                webcasts.add(new Webcast(
                        rs.getInt("ContentID"),
                        rs.getString("Title"),
                        rs.getDate("PublicationDate"),
                        Status.returnEnum("Status"),
                        rs.getString("URL"),
                        rs.getInt("Duration"),
                        webcastSpeakerDAO.getSpeakerForWebcast(rs.getInt("SpeakerID"))
                ));
            }

            return webcasts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }

        // Return null on error (code reached catch block)
        return null;
    }


    public ArrayList<ContentItem> getContentItemsForCourse(String courseName) {
        ArrayList<ContentItem> cItems = new ArrayList<>();
        cItems.addAll(getModulesForCourse(courseName));
        cItems.addAll(getWebcastsForCourse(courseName));

        return cItems;
    }

}
