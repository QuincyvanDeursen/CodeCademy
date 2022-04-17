package GUI.StatisticViews;

import Database.EnrollmentDAO;
import Database.ProgressDAO;
import Domain.Course;
import Domain.Student;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class StudentProgressModuleView {
    private final EnrollmentDAO enrollDAO = new EnrollmentDAO();
    private final ProgressDAO progressDAO = new ProgressDAO();
    private final Text title = new Text("Student module progressie!");
    private final ComboBox<Student> comboBoxStudent = new ComboBox<>();
    private final ComboBox<Course> comboBoxCourse = new ComboBox<>();
    private final Button checkButton = new Button("Check");
    private final Button showModulesButton = new Button("Show Modules");
    private final Text returningResult = new Text("progressie modules van een student: ");
    private ArrayList<String> listWithProgressDataFromOneStudent = new ArrayList<>();

    //    Returns a borderpane that shows the whole content that is made within this class.
    public BorderPane getPane() {
        BorderPane mainPane = new BorderPane();
        title.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 2em;");
        mainPane.setTop(this.title);

        this.comboBoxCourse.setVisible(false);
        this.checkButton.setVisible(false);
        this.returningResult.setVisible(false);

        mainPane.setCenter(getTexts());
        mainPane.setPadding(new Insets(100, 100, 100, 100));

        return mainPane;
    }

    //    Returns a grid pane that decides the design on the content that is shows.
    private Node getTexts() {
        GridPane getContext = new GridPane();

        getContext.add(comboBoxStudent, 0, 1);
        comboBoxStudent.setPrefWidth(150);
        comboBoxStudent.getItems().clear();
        for (Student student : enrollDAO.getDistinctEnrolledStudents()) {
            comboBoxStudent.getItems().add(student);
        }

        getContext.add(this.showModulesButton, 0, 2);
        getContext.add(comboBoxCourse, 0, 3);
        getContext.add(this.checkButton, 0, 4);
        buttonSetOnActionResult();

        getContext.add(new Text("Selecteer een Student!"), 0, 0);
        getContext.add(returningResult, 0, 5);

        getContext.setHgap(25);
        getContext.setVgap(25);

        return getContext;
    }

    //    Handles the setOnAction for the button.
    private void buttonSetOnActionResult() {
        this.showModulesButton.setOnAction(actionEvent -> {
            for (Course course : enrollDAO.getCoursesOfEnrolledStudent(comboBoxStudent.getValue())) {
                comboBoxCourse.getItems().add(course);
            }
            this.comboBoxCourse.setVisible(true);
            this.checkButton.setVisible(true);
            this.returningResult.setVisible(true);
        });


        this.checkButton.setOnAction(actionEvent -> {
            listWithProgressDataFromOneStudent = progressDAO.getModuleProgression(comboBoxStudent.getSelectionModel().getSelectedItem(), comboBoxCourse.getSelectionModel().getSelectedItem());
            if (listWithProgressDataFromOneStudent.size() == 0) {
                Alert warningAlert = new Alert(Alert.AlertType.ERROR);
                warningAlert.setContentText("Deze student is nog niet aan modules begonnen in deze cursus!");
                warningAlert.show();
            } else {
                this.returningResult.setText("progressie module van student:  " +
                        comboBoxStudent.getSelectionModel().getSelectedItem().toString() + "\n \n" +
                        printListWithData()
                );
                this.comboBoxStudent.setValue(null);
                this.comboBoxCourse.getItems().clear();
                this.comboBoxCourse.setVisible(false);
                this.checkButton.setVisible(false);
            }
        });


    }

    //    Returns a string with the data that is needed.
    private String printListWithData() {
        StringBuilder returningList = new StringBuilder();
        for (String modulePlusAverage : listWithProgressDataFromOneStudent) {
            returningList.append(modulePlusAverage + " % \n");
        }
        return returningList.toString();
    }
}
