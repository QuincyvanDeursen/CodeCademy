package Database;

import Domain.*;
import Domain.Module;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ContentItemDAO {

    // Retrieve a list of contentitems for a course

    // Retrieve a list of modules for a given course
    public ArrayList<Module> getModulesForCourse(String name) {
        // Query to retrieve all content items for a course
        String query = "Select * From Module INNER JOIN ModuleContactPerson as m ON Module.EmailContact = m.Email INNER JOIN ContentItem as C ON Module.ModuleID = c.ModuleID  where Title = '" + name + "'";
        // Create an arraylist to store the retrieved data
        ArrayList<Module> modules = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            // Loop through the result set
            while (rs.next()) {
                modules.add(
                        new Module(
                                rs.getInt("ModuleID"),
                                rs.getString("Title"),
                                rs.getDate("PublicationDate"),
                                Status.returnEnum("Status"),
                                rs.getDouble("Version"),
                                rs.getInt("SerialNumber"),
                                new ContactPersonModule(
                                        rs.getString("Name"),
                                        rs.getString("Email")
                                )
                        ));
                System.out.println("modulesArraylist : size " + modules.size());
            }
            return modules;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e + "getModulesforCourse");
        }
        finally{
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }

        // Return nothing on error (null)
        return null;
    }

    // Retrieve a list of webcasts for a given course
    public ArrayList<Webcast> getWebcastsForCourse(String name) {
        // Query to retrieve all webcasts for a course
        String query = "select * from Webcast INNER JOIN WebcastSpeaker AS W ON Webcast.SpeakerID = W.SpeakerID INNER JOIN ContentItem AS C ON Webcast.WebcastID = C.WebcastID WHERE Title = '" + name + "'";

        // Create an arraylist to store the retrieved data
        ArrayList<Webcast> webcasts = new ArrayList<>();

        // Create a prepared statement to prevent SQL injections
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("Making a Webcast object");
                webcasts.add(new Webcast(
                        rs.getInt("WebcastID"),
                        rs.getString("Title"),
                        rs.getDate("PublicationDate"),
                        Status.returnEnum("Status"),
                        rs.getString("URL"),
                        rs.getInt("Duration"),
                        new SpeakerWebcast(
                                rs.getInt("SpeakerID"),
                                rs.getString("NameSpeaker"),
                                rs.getString("OrganizationSpeaker")
                        )
                ));
            }

            return webcasts;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e + "getWebcastsForCourse");
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

        System.out.println("size of getContectItemsForCourse Arraylist Size " + cItems.size());
        return cItems;
    }

}
