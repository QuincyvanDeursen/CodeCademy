package GUI.StatisticViews;

import Database.EnrollDAO;
import Database.ProgressDAO;
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
    private EnrollDAO enrollDAO = new EnrollDAO();
    private ProgressDAO progressDAO = new ProgressDAO();
    private Text title = new Text("Student module progressie!");
    private ComboBox comboBoxSelectedEmail = new ComboBox<>();
    private ComboBox comboBoxSelectedCourse = new ComboBox<>();

    private Button checkButton = new Button("Check");
    private Button showModulesButton = new Button("Show Modules");
    private Text returningResult = new Text("progressie modules van een student: ");
    ArrayList<String> listWithProgressDataFromOneStudent = new ArrayList<>();

    public BorderPane getPane() {
        BorderPane mainPane = new BorderPane();
        title.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 2em;");
        mainPane.setTop(this.title);

        this.comboBoxSelectedCourse.setVisible(false);
        this.checkButton.setVisible(false);
        this.returningResult.setVisible(false);

        mainPane.setCenter(getTexts());
        mainPane.setPadding(new Insets(100, 100, 100, 100));

        return mainPane;
    }

    private Node getTexts() {
        GridPane getContext = new GridPane();

        getContext.add(comboBoxSelectedEmail, 0, 1);
        comboBoxSelectedEmail.setPrefWidth(150);
        comboBoxSelectedEmail.getItems().clear();
        for (String enrolls : enrollDAO.getDistinctEnrolledEmails()) {
            comboBoxSelectedEmail.getItems().add(enrolls);
        }

        getContext.add(this.showModulesButton,0,2);
        getContext.add(comboBoxSelectedCourse,0,3);
        getContext.add(this.checkButton, 0, 4);
        buttonSetOnActionResult();

        getContext.add(new Text("Selecteer een Student!"), 0, 0);
        getContext.add(returningResult, 0, 5);

        getContext.setHgap(25);
        getContext.setVgap(25);

        return getContext;
    }

    private void buttonSetOnActionResult() {

        this.showModulesButton.setOnAction(actionEvent -> {
            for (String course: enrollDAO.getCourseNamesFromEnrolledStudent(comboBoxSelectedEmail.getSelectionModel().getSelectedItem().toString())) {
                comboBoxSelectedCourse.getItems().add(course);
            }
            this.comboBoxSelectedCourse.setVisible(true);
            this.checkButton.setVisible(true);
            this.returningResult.setVisible(true);
        });


        this.checkButton.setOnAction(actionEvent -> {
            listWithProgressDataFromOneStudent = progressDAO.getModuleProgression(comboBoxSelectedEmail.getSelectionModel().getSelectedItem().toString(), comboBoxSelectedCourse.getSelectionModel().getSelectedItem().toString());
            if ( listWithProgressDataFromOneStudent.size() == 0) {
                Alert warningAlert = new Alert(Alert.AlertType.ERROR);
                warningAlert.setContentText("Deze student is nog niet aan modules begonnen in deze cursus!");
                warningAlert.show();
            } else {
                this.returningResult.setText("progressie module van een student:  " +
                        comboBoxSelectedEmail.getSelectionModel().getSelectedItem().toString() + "\n \n" +
                        printListWithData()
                );
            }
        });


    }

    private String printListWithData() {
        StringBuilder returingList = new StringBuilder();
        for (String modulePlusAverage : listWithProgressDataFromOneStudent) {
            returingList.append(modulePlusAverage + " % \n");
        }
        return returingList.toString();
    }
}
