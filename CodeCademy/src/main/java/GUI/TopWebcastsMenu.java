package GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TopWebcastsMenu {


    public Scene getView(Stage stage) {
        BorderPane mainPane = new BorderPane();

        Scene scene = new Scene(mainPane,500,500);
        return scene;
    }


}
