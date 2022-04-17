package Database;

import Domain.Gender;
import Domain.Student;
import java.sql.*;
import java.util.ArrayList;

public class StudentDAO {

    // method to retrieve all student records in the database.
    public ArrayList<Student> getStudentList() {
        String query = "SELECT * FROM Student";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            ArrayList<Student> studentList = new ArrayList<>();
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Student student = new Student(
                        rs.getString("Email"),
                        rs.getString("Name"),
                        rs.getDate("Birthdate").toLocalDate(),
                        Gender.valueToGenderEnum(rs.getString("Gender")),
                        rs.getString("City"),
                        rs.getString("PostalCode"),
                        rs.getString("Street"),
                        rs.getInt("HouseNr"),
                        rs.getString("Country"));
                studentList.add(student);
            }
            return studentList;
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

    //method to create a student in the database
    public boolean createStudent(Student student) {
        String query = "INSERT INTO Student VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, student.getEmail());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getBirthdate().toString());
            stmt.setString(4, student.getGender().toString());
            stmt.setString(5, student.getCity());
            stmt.setString(6, student.getPostalCode());
            stmt.setString(7, student.getStreet());
            stmt.setInt(8, student.getHouseNr());
            stmt.setString(9, student.getCountry());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally{
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    //Method to update a student in the database
    public boolean updateStudent(Student student, String originalEmail){
        String query = "UPDATE Student SET Email = ?, Name = ?, Birthdate = ?, Gender = ?, City = ?, PostalCode = ?, Street = ?, HouseNr = ?, Country = ? WHERE Email = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
             conn = DBConnection.getConnection();
             stmt = conn.prepareStatement(query);
            stmt.setString(1, student.getEmail());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getBirthdate().toString());
            stmt.setString(4, student.getGender().toString());
            stmt.setString(5, student.getCity());
            stmt.setString(6, student.getPostalCode());
            stmt.setString(7, student.getStreet());
            stmt.setInt(8, student.getHouseNr());
            stmt.setString(9, student.getCountry());
            stmt.setString(10, originalEmail);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return false;
    }

    //Method to find students in the database. This is not the method to find exactly one Student.
    //This method makes use of the like clause, so it may return multiple student results.
    //This method is used for a user-friendly search approach in the student table.
    public ArrayList<Student> findStudent(String email){
        String query = "SELECT * FROM student WHERE Email LIKE ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            ArrayList<Student> studentList = new ArrayList<>();
             conn = DBConnection.getConnection();
             stmt = conn.prepareStatement(query);
             stmt.setString(1, "%" + email + "%");
             rs = stmt.executeQuery();
            while (rs.next()) {
                Student student = new Student(
                        rs.getString("Email"),
                        rs.getString("Name"),
                        rs.getDate("Birthdate").toLocalDate(),
                        Gender.valueToGenderEnum(rs.getString("Gender")),
                        rs.getString("City"),
                        rs.getString("PostalCode"),
                        rs.getString("Street"),
                        rs.getInt("HouseNr"),
                        rs.getString("Country"));
                studentList.add(student);
            }
            return studentList;
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

    //Method to delete a student from the database.
    public boolean deleteStudent(String email){
        String query = "DELETE FROM student WHERE Email = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        finally{
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return  false;
    }
    public Student readStudent(String email){
        String query = "SELECT * FROM Student WHERE Email = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            rs = stmt.executeQuery();


            // Check if there is a result in the set
            if(rs.next()){
                return new Student(
                        rs.getString("Email"),
                        rs.getString("Name"),
                        rs.getDate("Birthdate").toLocalDate(),
                        Gender.valueToGenderEnum(rs.getString("Gender")),
                        rs.getString("City"),
                        rs.getString("PostalCode"),
                        rs.getString("Street"),
                        rs.getInt("HouseNr"),
                        rs.getString("Country")
                );
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }

        // Return null on error
        return null;
    }
}
