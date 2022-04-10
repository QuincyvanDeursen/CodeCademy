package GUI;

import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class CourseCompletedStats {
    Text title = new Text("Courses completed!");

    public BorderPane getPane () {
        BorderPane mainPane = new BorderPane();

        title.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 2em;");
        mainPane.setTop(this.title);

        return mainPane;
    }
}
