package Database;

import Domain.Enrollment;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class EnrollDAO {

    private StudentDAO studentDAO = new StudentDAO();
    private CourseDAO courseDAO = new CourseDAO();

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
                System.out.println("Enrollment object total: " + enrollmentList.size());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getStudentList");
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
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, enrollment.getRegistrationDate().toString());
            stmt.setNull(2, 0);
            stmt.setString(3, course);
            stmt.setString(4, mail);
            stmt.executeUpdate();


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

    public boolean updateRecord(String comboboxMail, String comboboxCourse, String cellMail, String cellCourse) {
        String query = "UPDATE Enrollment SET Email = ? , CourseName = ? WHERE Email = ? AND CourseName = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, comboboxMail);
            stmt.setString(2, comboboxCourse);
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
}
