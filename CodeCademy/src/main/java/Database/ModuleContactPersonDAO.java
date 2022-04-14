package Database;

import Domain.ContactPersonModule;
import Domain.SpeakerWebcast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModuleContactPersonDAO {

    //Retrieve the ModuleContactPerson for a given email.
    public ContactPersonModule getModuleContactPerson(String email) {
        String query = "SELECT * FROM ModuleContactPerson WHERE Email = ?";

        // Create a prepared statement to prevent SQL injections
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            while (rs.next()) {
                return new ContactPersonModule(rs.getString("Email"),
                        rs.getString("Name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
