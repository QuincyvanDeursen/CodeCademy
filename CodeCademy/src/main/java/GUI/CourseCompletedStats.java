package GUI;

import Database.CourseDAO;
import Database.StatisticsDAO;
import Domain.Course;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class CourseCompletedStats {

    private StatisticsDAO getStatsData = new StatisticsDAO();
    private CourseDAO courseDAO = new CourseDAO();

    private Text title = new Text("Courses completed!");

    private ComboBox comboBoxSelectedCourse = new ComboBox<>();

    private Button checkButton = new Button("Check");

    private Text returningResult = new Text("Amount of students completing the course: ");

    public BorderPane getPane () {

        BorderPane mainPane = new BorderPane();
        title.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 2em;");
        mainPane.setTop(this.title);

        mainPane.setCenter(getTexts());
        mainPane.setPadding(new Insets(100,100,100,100));

        return mainPane;
    }

    private Node getTexts() {
        GridPane getContext = new GridPane();

        getContext.add(comboBoxSelectedCourse,0,1);
        comboBoxSelectedCourse.setPrefWidth(150);
        comboBoxSelectedCourse.getItems().clear();
        for (Course course : courseDAO.getCourseList()) {
            comboBoxSelectedCourse.getItems().add(course.getCourseName());
        }
        getContext.add(this.checkButton,0,2);
        this.checkButton.setOnAction(actionEvent -> {
            if (getStatsData.getAmountOfCourseCompleted(comboBoxSelectedCourse.getSelectionModel().getSelectedItem().toString()) == -1 ) {
                returningResult.setText("Amount of students completing the course: No one completed this course yet!");
            } else {
                this.returningResult.setText("Amount of students completing the course: '" +
                        comboBoxSelectedCourse.getSelectionModel().getSelectedItem().toString() +
                        "' is: " + getStatsData.getAmountOfCourseCompleted(comboBoxSelectedCourse.getValue().toString())
                );
            }
        });


        getContext.add(new Text("Select a course!"),0,0);
        getContext.add(returningResult,0,3);

        getContext.setHgap(25);
        getContext.setVgap(25);



        return getContext;
    }


}
