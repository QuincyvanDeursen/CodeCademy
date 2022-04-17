package GUI.StatisticViews;

import Database.EnrollmentDAO;
import Database.ProgressDAO;
import Domain.Course;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class AverageModulesView {
    private final ProgressDAO progressDAO = new ProgressDAO();
    private final EnrollmentDAO enrollDAO = new EnrollmentDAO();
    private final Text title = new Text("Gemiddelde Module progressie!");
    private final ComboBox<Course> comboBoxCourse = new ComboBox<>();
    private final Button checkButton = new Button("Check");
    private final Text returningResult = new Text("Gemiddelde progressie modules van de cursus: ");
    private ArrayList<String> listWithAverageData = new ArrayList<>();

    //    Returns a borderpane that shows the whole content that is made within this class.
    public BorderPane getPane() {
        BorderPane mainPane = new BorderPane();
        title.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 2em;");
        mainPane.setTop(this.title);

        mainPane.setCenter(getTexts());
        mainPane.setPadding(new Insets(100, 100, 100, 100));

        return mainPane;
    }

    //    Returns a grid pane that decides the design on the content that is shows.
    private Node getTexts() {
        GridPane getContext = new GridPane();

        getContext.add(comboBoxCourse, 0, 1);
        comboBoxCourse.setPrefWidth(150);
        comboBoxCourse.getItems().clear();
        for (Course course : enrollDAO.getDistinctEnrolledCourses()) {
            comboBoxCourse.getItems().add(course);
        }
        getContext.add(this.checkButton, 0, 2);
        buttonSetOnActionResult();

        getContext.add(new Text("Selecteer een cursus!"), 0, 0);
        getContext.add(returningResult, 0, 3);

        getContext.setHgap(25);
        getContext.setVgap(25);

        return getContext;
    }

    //    Handles the setOnAction for the button.
    private void buttonSetOnActionResult() {
        this.checkButton.setOnAction(actionEvent -> {
            listWithAverageData = progressDAO.getAverageProgressionOfAllModulesFromCourse(comboBoxCourse.getSelectionModel().getSelectedItem());
            if (progressDAO.getAverageProgressionOfAllModulesFromCourse(comboBoxCourse.getSelectionModel().getSelectedItem()) == null) {
                returningResult.setText("Gemiddelde progressie van de modulen van een cursus: Hier is geen data van");
            } else {
                this.returningResult.setText("Gemiddelde progressie van de modulen van een cursus: " +
                        comboBoxCourse.getSelectionModel().getSelectedItem().toString() + "\n \n" +
                        printListWithData()
                );
            }
        });
    }

    //    Returns a string with the data that is needed.
    private String printListWithData() {
        StringBuilder returningList = new StringBuilder();
        for (String modulePlusAverage : listWithAverageData) {
            returningList.append(modulePlusAverage + " % \n");
        }
        return returningList.toString();
    }
}
