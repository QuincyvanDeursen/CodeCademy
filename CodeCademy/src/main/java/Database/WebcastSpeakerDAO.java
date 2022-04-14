package Database;

import Domain.SpeakerWebcast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class WebcastSpeakerDAO {

    //Retrieve the WebcastSpeaker for a given id.
    public SpeakerWebcast getSpeakerForWebcast(int id) {
        // Query to retrieve all webcasts for a course
        String query = "SELECT * FROM WebcastSpeaker WHERE SpeakerID = ?";

        // Create a prepared statement to prevent SQL injections
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                return new SpeakerWebcast(rs.getInt("SpeakerID"),
                        rs.getString("NameSpeaker"),
                        rs.getString("OrganizationSpeaker")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}


