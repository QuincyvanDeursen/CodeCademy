package GUI;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.concurrent.Flow;

public class MainMenu extends Application implements EventHandler {

    private Label crudLabel = new Label("CRUD");
    private Label statisticsLabel = new Label("Statistieken");
    private Button studentButton = new Button("Cursisten");
    private Button enrollButton = new Button("Inschrijvingen");
    private Button statisticsButton = new Button("Statistieken");
    private Stage stage;


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Thomas van Otterloo (2186082) / Quincy van Deursen (2113709)");
        stage.setScene(this.getView(stage));
        stage.show();
    }


    //    Creating a pane for the scene.
    public Scene getView(Stage stage) {
        this.stage = stage;
        FlowPane flowPane = getMainPane(stage);

        setPaneStyling(flowPane);
        setButtonStyling(this.enrollButton);
        setButtonStyling(this.studentButton);
        setButtonStyling(this.statisticsButton);
        setLabelStyling(this.crudLabel);
        setLabelStyling(this.statisticsLabel);

        this.enrollButton.setOnAction(this::handle);
        this.studentButton.setOnAction(this::handle);
        this.statisticsButton.setOnAction(this::handle);


        Scene scene = new Scene(flowPane, 250, 500);
        return scene;
    }

    //    Creating gridPane and placing buttons and labels.
    private FlowPane getMainPane(Stage window) {
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.setColumnHalignment(HPos.CENTER);
        flowPane.getChildren().addAll(crudLabel, studentButton, enrollButton, statisticsLabel, statisticsButton);
        return flowPane;
    }

    // Creates the style for the mainPane.
    private static void setPaneStyling(FlowPane mainPane) {
        mainPane.setPadding(new Insets(25, 25, 25, 25));
        mainPane.setVgap(50);
        mainPane.setHgap(40);
        mainPane.setStyle("-fx-background-color:#FAF3DC");
    }

    //creates the style for the buttons
    private static void setButtonStyling(Button button) {
        button.setStyle(
                "-fx-background-color:#2596be;" +
                        "-fx-font-size: 2em;" +
                        " -fx-background-radius: 12;" +
                        "-fx-effect: dropshadow( gaussian , rgba(0,0,0,5) , 4,0,0,2 );" +
                        "-fx-font-weight: bold;"
        );
    }

    //creates the style for the labels
    private static void setLabelStyling(Label label) {
        label.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 2em;");
    }

    //Event handler for the buttons.
    @Override
    public void handle(Event event) {
        if (event.getSource() == studentButton) {
            StudentMenu studentMenu = new StudentMenu();
            stage.setScene(studentMenu.getView(stage));
            return;
        }
//       if (event.getSource() == enrollButton){
//             EnrollMenu enrollMenu = new EnrollMenu();
//            stage.setScene(enrollMenu.getView(stage));
//            return;
//        }
//        if (event.getSource() == statisticsButton){
//             StatisticsMenu statisticsMenu = new StatisticsMenu();
//            stage.setScene(statisticsMenu.getView(stage));
//        }
    }
}
