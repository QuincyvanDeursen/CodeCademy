package Database;

import Domain.Gender;
import Domain.Student;
import java.sql.*;
import java.util.ArrayList;

public class StudentDAO {

    // method to retrieve all student records in the database.
    public ArrayList<Student> getStudentList() {
        ArrayList<Student> studentList = new ArrayList<>();
        String query = "SELECT * FROM Student";

        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

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
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    //method to create a student in the database
    public boolean createStudent(Student student) {
        String query = "INSERT INTO Student VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(query);
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
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Method to update a student in the database
    public boolean updateStudent(Student student, String originalEmail){
        String query = "UPDATE Student SET Email = ?, Name = ?, Birthdate = ?, Gender = ?, City = ?, PostalCode = ?, Street = ?, HouseNr = ?, Country = ? WHERE Email = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
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
            con.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Method to find a particular student in the database
    public ArrayList<Student> findStudent(String email){
        ArrayList<Student> studentList = new ArrayList<>();
        String query = "SELECT * FROM student WHERE Email LIKE '%" + email + "%'";
        try {
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
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
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    public boolean deleteStudent(String email){
        String query = "DELETE FROM student WHERE Email = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, email);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return  false;
        }
    }
}
