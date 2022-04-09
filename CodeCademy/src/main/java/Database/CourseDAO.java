package Database;

import Domain.Course;
import Domain.Level;

import java.sql.*;
import java.util.ArrayList;

public class CourseDAO {
    ContentItemDAO contentItemDAO = new ContentItemDAO();


    public Course readCourse(String name) {
        String query = "SELECT * FROM Course WHERE CourseName = '" + name + "'";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Course object being made");
                return new Course(
                        rs.getString("CourseName"),
                        rs.getString("Topic"),
                        Level.returnEnum(rs.getString("Level")),
                        rs.getString("Description"),
                        null,
                        contentItemDAO.getContentItemsForCourse(name)
                );
            }
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println("readCourse");
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
        return null;
    }

    public ArrayList<Course> getCourseList() {
        ArrayList<Course> courseArrayList = new ArrayList<>();
        String query = "Select * from Course";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                courseArrayList.add(new Course(
                        rs.getString("CourseName"),
                        rs.getString("Topic"),
                        Level.returnEnum(rs.getString("Level")),
                        rs.getString("Description"),
                        null,
                        null
                ));
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
        return courseArrayList;
    }

}
