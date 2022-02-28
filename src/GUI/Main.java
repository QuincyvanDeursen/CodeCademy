package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Hello World!");


        StackPane root = new StackPane();
        stage.setScene(new Scene(root, 300, 250));
        stage.show();
    }
}
