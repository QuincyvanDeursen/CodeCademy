package Database;

import Domain.*;
import Domain.Module;

import java.sql.*;
import java.util.ArrayList;

public class ContentItemDAO {
    private final WebcastSpeakerDAO  webcastSpeakerDAO = new WebcastSpeakerDAO();
    private final ModuleContactPersonDAO moduleContactPersonDAO = new ModuleContactPersonDAO();


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

}
