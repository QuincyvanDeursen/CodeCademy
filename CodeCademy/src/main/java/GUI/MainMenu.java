package GUI;

import GUI.CRUDViews.EnrollmentMenu;
import GUI.CRUDViews.StudentMenu;
import GUI.ProgressViews.ProgressMenu;
import GUI.StatisticViews.StatisticsMenu;
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
import javafx.stage.Stage;

public class MainMenu extends Application implements EventHandler {

    private final Label crudLabel = new Label("CRUD");
    private final Label statisticsLabel = new Label("Statistieken");
    private final Label progressLabel = new Label("Update Voortgang");
    private final Button studentButton = new Button("Cursisten");
    private final Button enrollButton = new Button("Inschrijvingen");
    private final Button statisticsButton = new Button("Statistieken");
    private final Button progressButton = new Button("Voortgang");
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
        setButtonStyling(this.progressButton);
        setLabelStyling(this.progressLabel);
        setLabelStyling(this.crudLabel);
        setLabelStyling(this.statisticsLabel);

        this.enrollButton.setOnAction(this::handle);
        this.studentButton.setOnAction(this::handle);
        this.statisticsButton.setOnAction(this::handle);
        this.progressButton.setOnAction(this::handle);


        Scene scene = new Scene(flowPane, 250, 700);
        return scene;
    }

    //    Creating gridPane and placing buttons and labels.
    private FlowPane getMainPane(Stage window) {
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.setColumnHalignment(HPos.CENTER);
        flowPane.getChildren().addAll(crudLabel, studentButton, enrollButton, statisticsLabel, statisticsButton, progressLabel, progressButton);
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
       if (event.getSource() == enrollButton){
             EnrollmentMenu enrollmentMenu = new EnrollmentMenu();
            stage.setScene(enrollmentMenu.getView(stage));
            return;
        }
        if (event.getSource() == statisticsButton){
             StatisticsMenu statisticsMenu = new StatisticsMenu();
            stage.setScene(statisticsMenu.getView(stage));
        }

        if (event.getSource() == progressButton){
            ProgressMenu progressMenu = new ProgressMenu();
            stage.setScene(progressMenu.getView(stage));
        }
    }
}
