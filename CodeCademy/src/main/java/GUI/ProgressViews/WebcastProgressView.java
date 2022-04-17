package GUI.ProgressViews;

import Database.EnrollmentDAO;
import Database.ProgressDAO;
import Domain.*;
import InputVerification.NumericRangeTool;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

public class WebcastProgressView implements EventHandler {
    private final ProgressDAO progressDAO = new ProgressDAO();
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private final Label title = new Label("Update de voortgang van een webast van een student.");
    private final ComboBox<Student> comboBoxStudent = new ComboBox<>();
    private final ComboBox<Course> comboBoxCourse = new ComboBox<>();
    private final ComboBox<ContentItem> comboBoxWebcast = new ComboBox<>();
    private final TextField progressTextField = new TextField();
    private final Label selectEmailLabel = new Label("Selecteer een email");
    private final Label selectCourseLabel = new Label("Selecteer een cursus");
    private final Label selectModuleLabel = new Label("Selecteer een webcast");
    private final Label setProgressLabel = new Label("Voer de progressie(%) in");
    private final Button showCourseButton = new Button("Toon Cursussen");
    private final Button showWebcastButton = new Button("Toon webcasts");
    private final Button updateButton = new Button("Update");

    //    Returns a BorderPane that will show content that is made within this class.
    public BorderPane getPane() {
        BorderPane mainPane = new BorderPane();
        title.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 1.4em;");
        mainPane.setTop(this.title);

        mainPane.setCenter(getTexts());
        mainPane.setPadding(new Insets(30, 100, 100, 100));

        return mainPane;
    }

    //    Returns a grid pane that shows the design of the page.
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
        gridPane.add(this.showWebcastButton, 0 ,5);
        gridPane.add(this.comboBoxWebcast, 1 ,5);
        gridPane.add(setProgressLabel, 1, 6);
        gridPane.add(this.progressTextField, 1, 7);
        gridPane.add(this.updateButton, 0, 7);

        for (Student student: enrollmentDAO.getDistinctEnrolledStudents()) {
            this.comboBoxStudent.getItems().add(student);
        }

        comboBoxStudent.valueProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observableValue, Student student, Student t1) {
                if (student != null) {
                    restoreView();
                }
            }
        });
        restoreView();




        showCourseButton.setOnAction(this::handle);
        showWebcastButton.setOnAction(this::handle);
        updateButton.setOnAction(this::handle);

        gridPane.setHgap(25);
        gridPane.setVgap(25);

        return gridPane;
    }

    //    Handles the setOnAction buttons.
    @Override
    public void handle(Event event) {
        if (event.getSource() ==showCourseButton){
            showCourseButtonFunctionality();
        }
        if (event.getSource() == showWebcastButton){
            showModuleButtonFunctionality();
        }
        if (event.getSource() ==updateButton){
            updateButtonFunctionality();
        }
    }

    //    Shows the next step once you chose an email.
    private void showCourseButtonFunctionality(){
        if (comboBoxStudent.getValue() == null){
            warningMessage("Selecteer eerst een email.");
            return;
        }
        for (Course course: enrollmentDAO.getCoursesOfEnrolledStudent(this.comboBoxStudent.getValue())){
            this.comboBoxCourse.getItems().add(course);
        }

        this.comboBoxCourse.setVisible(true);
        this.showWebcastButton.setVisible(true);
        return;
    }

    //    Shows the next step once you chose a course.
    private void showModuleButtonFunctionality(){
        if (comboBoxCourse.getValue() == null){
            warningMessage("Selecteer eerst een cursus.");
            return;
        }
        for (ContentItem contentItem: this.comboBoxCourse.getValue().getContentItems()){
            if (contentItem instanceof Webcast){
                this.comboBoxWebcast.getItems().add(contentItem);
            }
        }

        this.selectModuleLabel.setVisible(true);
        this.comboBoxWebcast.setVisible(true);
        this.setProgressLabel.setVisible(true);
        this.updateButton.setVisible(true);
        this.progressTextField.setVisible(true);
        return;
    }

    //    Handles the SetOnAction on the update button.
    private void updateButtonFunctionality(){
        if (comboBoxWebcast.getValue() == null){
            warningMessage("Selecteer eerst een webcast.");
            return;
        }
        if (progressTextField.getText() != null && NumericRangeTool.isValidPercentage(Integer.parseInt(progressTextField.getText()))){
            Progress progress = new Progress(LocalDate.now(), comboBoxStudent.getValue(), comboBoxWebcast.getValue(),(Integer.parseInt(progressTextField.getText())));
            progressDAO.updateProgress(progress);
            confirmationMessage("Progressie geupdatet!");
            restoreView();
            return;
        } else {
            warningMessage("Vul een geldige percentage (0-100) in!");
        }

    }

    //    Sets the page back to step one once a record has been updated.
    private void restoreView(){
        this.comboBoxCourse.getItems().clear();
        this.comboBoxWebcast.getItems().clear();
        this.selectCourseLabel.setVisible(false);
        this.comboBoxCourse.setVisible(false);
        this.selectModuleLabel.setVisible(false);
        this.comboBoxWebcast.setVisible(false);
        this.showWebcastButton.setVisible(false);
        this.updateButton.setVisible(false);
        this.setProgressLabel.setVisible(false);
        this.progressTextField.setVisible(false);
    }

    //    Shows a confirmation message on screen.
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
