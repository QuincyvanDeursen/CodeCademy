package GUI.StatisticViews;

import Database.EnrollmentDAO;
import Database.ProgressDAO;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class AverageModulesView {
    private ProgressDAO progressDAO = new ProgressDAO();
    private EnrollmentDAO enrollDAO = new EnrollmentDAO();
    private Text title = new Text("Gemiddelde Module progressie!");
    private ComboBox comboBoxSelectedCourse = new ComboBox<>();
    private Button checkButton = new Button("Check");
    private Text returningResult = new Text("Gemiddelde progressie modules van de cursus: ");
    ArrayList<String> listWithAverageData = new ArrayList<>();

    public BorderPane getPane() {
        BorderPane mainPane = new BorderPane();
        title.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 2em;");
        mainPane.setTop(this.title);

        mainPane.setCenter(getTexts());
        mainPane.setPadding(new Insets(100, 100, 100, 100));

        return mainPane;
    }

    private Node getTexts() {
        GridPane getContext = new GridPane();

        getContext.add(comboBoxSelectedCourse, 0, 1);
        comboBoxSelectedCourse.setPrefWidth(150);
        comboBoxSelectedCourse.getItems().clear();
        for (String course : enrollDAO.getDistinctEnrolledCourseNames()) {
            comboBoxSelectedCourse.getItems().add(course);
        }
        getContext.add(this.checkButton, 0, 2);
        buttonSetOnActionResult();

        getContext.add(new Text("Selecteer een cursus!"), 0, 0);
        getContext.add(returningResult, 0, 3);

        getContext.setHgap(25);
        getContext.setVgap(25);

        return getContext;
    }

    private void buttonSetOnActionResult() {
        this.checkButton.setOnAction(actionEvent -> {
            listWithAverageData = progressDAO.getAverageProgressionOfAllModulesFromCourse(comboBoxSelectedCourse.getSelectionModel().getSelectedItem().toString());
            if (progressDAO.getAverageProgressionOfAllModulesFromCourse(comboBoxSelectedCourse.getSelectionModel().toString()) == null) {
                returningResult.setText("Gemiddelde progressie modules van de cursus: Hier is geen data van");
            } else {
                this.returningResult.setText("Gemiddelde progressie modules van de cursus: " +
                        comboBoxSelectedCourse.getSelectionModel().getSelectedItem().toString() + "\n \n" +
                        printListWithData()
                );
            }
        });
    }

    private String printListWithData() {
        StringBuilder returingList = new StringBuilder();
        for (String modulePlusAverage : listWithAverageData) {
            returingList.append(modulePlusAverage + " % \n");
        }
        return returingList.toString();
    }
}
