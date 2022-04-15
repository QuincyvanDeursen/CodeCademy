package GUI.ProgressViews;

import Database.EnrollmentDAO;
import Domain.ContentItem;
import Domain.Course;
import Domain.Enrollment;
import Domain.Module;
import Domain.Student;
import InputVerification.NumericRangeTool;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ModuleProgressView implements EventHandler {
    private EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private Label title = new Label("Update de voortgang van een module van een student.");
    private ComboBox<Student> comboBoxStudent = new ComboBox<>();
    private ComboBox<Course> comboBoxCourse = new ComboBox<>();
    private ComboBox<ContentItem> comboBoxModule = new ComboBox<>();
    private TextField progressTextField = new TextField();
    private Label selectEmailLabel = new Label("Selecteer een email");
    private Label selectCourseLabel = new Label("Selecteer een cursus");
    private Label selectModuleLabel = new Label("Selecteer een module");
    private Label setProgressLabel = new Label("Voer de progressie(%) in");
    private Button showCourseButton = new Button("Toon Cursussen");
    private Button showModuleButton = new Button("Toon Modules");
    private Button updateButton = new Button("Update");


    public BorderPane getPane() {
        BorderPane mainPane = new BorderPane();
        title.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 2em;");
        mainPane.setTop(this.title);

        mainPane.setCenter(getTexts());
        mainPane.setPadding(new Insets(30, 100, 100, 100));

        return mainPane;
    }

    private Node getTexts() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        comboBoxStudent.setPrefWidth(150);
        comboBoxStudent.getItems().clear();
        gridPane.add(selectEmailLabel, 1, 0);
        gridPane.add(comboBoxStudent, 1, 1);
        gridPane.add(selectCourseLabel, 1, 2);
        gridPane.add(this.comboBoxCourse, 1, 3);
        gridPane.add(this.showCourseButton, 0,3);
        gridPane.add(this.selectModuleLabel, 1, 4);
        gridPane.add(this.showModuleButton, 0 ,5);
        gridPane.add(this.comboBoxModule, 1 ,5);
        gridPane.add(setProgressLabel, 1, 6);
        gridPane.add(this.progressTextField, 1, 7);
        gridPane.add(this.updateButton, 0, 7);

        for (Student student: enrollmentDAO.getDistinctEnrolledStudents()) {
            this.comboBoxStudent.getItems().add(student);
        }

        this.selectCourseLabel.setVisible(false);
        this.comboBoxCourse.setVisible(false);
        this.selectModuleLabel.setVisible(false);
        this.comboBoxModule.setVisible(false);
        this.showModuleButton.setVisible(false);
        this.updateButton.setVisible(false);
        this.setProgressLabel.setVisible(false);
        this.progressTextField.setVisible(false);



        showCourseButton.setOnAction(this::handle);
        showModuleButton.setOnAction(this::handle);
        updateButton.setOnAction(this::handle);

        gridPane.setHgap(25);
        gridPane.setVgap(25);

        return gridPane;
    }


    @Override
    public void handle(Event event) {
        if (event.getSource() ==showCourseButton){
            showCourseButtonFunctionality();
        }
        if (event.getSource() ==showModuleButton){
            showModuleButtonFunctionality();
        }
        if (event.getSource() ==updateButton){
            updateButtonFunctionality();
        }
    }

    private void showCourseButtonFunctionality(){
        if (comboBoxStudent.getValue() == null){
            warningMessage("Selecteer eerst een email.");
            return;
        }
        for (Course course: enrollmentDAO.getCoursesOfEnrolledStudent(this.comboBoxStudent.getValue().getEmail())){
            this.comboBoxCourse.getItems().add(course);
        }

        this.comboBoxCourse.setVisible(true);
        this.showModuleButton.setVisible(true);
        return;
    }

    private void showModuleButtonFunctionality(){
        if (comboBoxCourse.getValue() == null){
            warningMessage("Selecteer eerst een cursus.");
            return;
        }
        for (ContentItem contentItem: this.comboBoxCourse.getValue().getContentItems()){
            if (contentItem instanceof Module){
                this.comboBoxModule.getItems().add(contentItem);
            }
        }

        this.selectModuleLabel.setVisible(true);
        this.comboBoxModule.setVisible(true);
        this.setProgressLabel.setVisible(true);
        this.updateButton.setVisible(true);
        this.progressTextField.setVisible(true);
        return;
    }

    private void updateButtonFunctionality(){
        if (comboBoxModule.getValue() == null){
            warningMessage("Selecteer eerst een module.");
            return;
        }
        if (progressTextField.getText() == null || !NumericRangeTool.isValidPercentage(Integer.parseInt(progressTextField.getText()))){
            warningMessage("Vul een geldige percentage (0-100) in!");
            return;
        }

        this.selectCourseLabel.setVisible(false);
        this.comboBoxCourse.setVisible(false);
        this.selectModuleLabel.setVisible(false);
        this.comboBoxModule.setVisible(false);
        this.showModuleButton.setVisible(false);
        this.updateButton.setVisible(false);
        this.setProgressLabel.setVisible(false);
        this.progressTextField.setVisible(false);
        
    }

    private static void confirmationMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.show();
    }

    //this method shows a warning alert
    private static void warningMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.show();
    }
}
