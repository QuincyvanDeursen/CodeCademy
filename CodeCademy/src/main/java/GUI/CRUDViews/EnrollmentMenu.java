package GUI.CRUDViews;

import Database.CourseDAO;
import Database.EnrollmentDAO;
import Database.StudentDAO;
import Domain.Course;
import Domain.Enrollment;
import Domain.Student;
import GUI.MainMenu;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class EnrollmentMenu {


    private MainMenu menu = new MainMenu();
    private EnrollmentDAO enrollDAO = new EnrollmentDAO();
    private StudentDAO studentDAO = new StudentDAO();
    private CourseDAO courseDAO = new CourseDAO();

    public Button backBtn = new Button("Terug");
    private final Label introText = new Label("Schrijf een student in!");

    private final Label mailText = new Label("* email: ");
    private final TextField mailTextField = new TextField();
    private final Label errorMail = new Label("");

    private Label courseName = new Label("* cursus: ");
    private ComboBox<Course> comboBoxCourse = new ComboBox();
    private ComboBox<Student> comboBoxStudent = new ComboBox();
    private final Label errorCourse = new Label("");


    private Button btnInsert = new Button("Toevoegen");
    private Button btnDelete = new Button("Verwijderen");
    private Button btnUpdate = new Button("Update");


    String emailCellValue = "";
    String courseCellValue = "";

    private TableView<Enrollment> enrollTable = new TableView<>();
    private TableColumn<Enrollment, String> emailCol = new TableColumn<>("Email");
    private TableColumn<Enrollment, LocalDate> dateCol = new TableColumn<>("Date");
    private TableColumn<Enrollment, String> courseCol = new TableColumn<>("CourseName");


    public Scene getView(Stage stage) {
        BorderPane mainPane = new BorderPane();
        mainPane.setStyle("-fx-background-color: #FAF3DC;");
        mainPane.setPadding(new Insets(25, 25, 25, 25));
        GridPane userInputPane = getContent();
        GridPane buttonsPane = getButtons(stage);

        mainPane.setCenter(userInputPane);
        mainPane.setBottom(buttonsPane);
        mainPane.setRight(getTableWithData());
        Scene scene = new Scene(mainPane);
        return scene;
    }


    private GridPane getButtons(Stage stage) {

        GridPane pane = new GridPane();

        pane.add(backBtn, 0, 0);
        backBtn.setOnAction(actionEvent -> stage.setScene(menu.getView(stage)));
        pane.add(btnInsert, 1, 0);
        btnInsert.setOnAction(actionEvent -> insertRecord());
        pane.add(btnUpdate, 2, 0);
        btnUpdate.setOnAction(actionEvent -> updateRecord());
        pane.add(btnDelete, 3, 0);
        btnDelete.setOnAction(actionEvent -> deleteRecord());

        setCellValueToComboBoxOnClick();


        pane.setPadding(new Insets(50, 50, 50, 50));
        pane.setVgap(30);
        pane.setHgap(30);
        return pane;
    }

    private void setCellValueToComboBoxOnClick() {
        try {
            this.enrollTable.setOnMouseClicked(e -> {
                Enrollment enrollment = enrollTable.getItems().get(enrollTable.getSelectionModel().getSelectedIndex());
                this.comboBoxStudent.setValue(enrollment.getStudent());
                this.comboBoxCourse.setValue(enrollment.getCourse());

                this.emailCellValue = enrollment.getStudent().getEmail();
                this.courseCellValue = enrollment.getCourse().getCourseName();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateRecord() {
        try {
            if (enrollTable.getSelectionModel().getSelectedItem() == null) {
                Alert warning = new Alert(Alert.AlertType.ERROR);
                warning.setContentText("Er is geen record geselecteerd om te updaten.");
                warning.show();
            } else {
                Enrollment enrollment = new Enrollment(comboBoxStudent.getValue(), comboBoxCourse.getValue(), LocalDate.now());
                enrollDAO.updateRecord(enrollment, this.emailCellValue, this.courseCellValue);
                refreshTable();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showDuplicationError();
        }

    }

    private void deleteRecord() {
        if (enrollTable.getSelectionModel().getSelectedItem() == null) {
            Alert warning = new Alert(Alert.AlertType.ERROR);
            warning.setContentText("Er is geen record geselecteerd om te verwijderen.");
            warning.show();
        } else {
            TablePosition pos = enrollTable.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            Enrollment enrollment = enrollTable.getItems().get(row);
            enrollDAO.deleteRecord(enrollment);
            refreshTable();
        }

    }

    private void insertRecord() {
        if (!enrollDAO.addRecord(comboBoxCourse.getValue().toString(), comboBoxStudent.getValue().toString())) {
            showDuplicationError();
        }
        refreshTable();
    }

    private void refreshTable() {
        this.enrollTable.getItems().clear();
        ArrayList<Enrollment> enrollList = enrollDAO.getEnrollmentList();
        for (Enrollment enrollment : enrollList) {
            this.enrollTable.getItems().add(enrollment);
        }
    }

    //Method which returns the Enrollment tableview
    public TableView<Enrollment> getTableWithData() {
        enrollTable.getColumns().clear();
        enrollTable.getColumns().addAll(
                emailCol,
                dateCol,
                courseCol
        );
        setEnrollmentDataIntoTable();
        enrollTable.setPrefWidth(400);
        enrollTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return enrollTable;
    }

    public void setEnrollmentDataIntoTable() {
        ArrayList<Enrollment> enrollList = enrollDAO.getEnrollmentList();
        this.emailCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStudent().getEmail()));
        this.dateCol.setCellValueFactory(new PropertyValueFactory<>("RegistrationDate"));
        this.courseCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCourse().getCourseName()));
        for (Enrollment enrollment : enrollList) {
            this.enrollTable.getItems().add(enrollment);
        }
    }

    //    De code die de exceptie gebruikt om te kijken of er duplicaties zitten in het tabel.
    private boolean isDuplicateEntryException(SQLException exc) {
        return exc.getSQLState().equals("23000");
    }


    private GridPane getContent() {
        GridPane gridPane = new GridPane();
        gridPane.add(introText, 0, 0);
        introText.setFont(Font.font("Verdana", 15));

        gridPane.add(mailText, 0, 1);
        gridPane.add(comboBoxStudent, 1, 1);
        comboBoxStudent.setPrefWidth(150);
        comboBoxStudent.getItems().clear();
        for (Student student : studentDAO.getStudentList()) {
            comboBoxStudent.getItems().add(student);
        }

        gridPane.add(errorMail, 2, 1);
        gridPane.add(courseName, 0, 2);
        gridPane.add(comboBoxCourse, 1, 2);
        gridPane.add(errorCourse, 2, 2);
        comboBoxCourse.setPrefWidth(150);
        comboBoxCourse.getItems().clear();
        for (Course course : courseDAO.getCourseList()) {
            comboBoxCourse.getItems().add(course);
        }


        gridPane.setPadding(new Insets(50, 50, 50, 50));
        gridPane.setVgap(30);
        gridPane.setHgap(30);
        return gridPane;
    }

    public void showDuplicationError() {
        Alert warningAlert = new Alert(Alert.AlertType.WARNING);
        warningAlert.setContentText("Er bestaat al een record met deze combinatie!");
        warningAlert.show();
    }
}
