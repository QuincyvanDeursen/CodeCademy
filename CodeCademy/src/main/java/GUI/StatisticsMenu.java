package GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class StatisticsMenu {

    private MainMenu mainMenu = new MainMenu();

    private Button topWebcasts = new Button("Top 3 Webcasts");
    private Button averageModuleProgress = new Button(" Average Module");
    private Button studentModuleProgress = new Button("Student Module");
    private Button courseCompleteAmount = new Button("Course Complete");
    public Button backBtn = new Button("Terug");

//     creates a view
    public Scene getView(Stage stage) {
        BorderPane mainPane = new BorderPane();
        mainPane.setPadding(new Insets(0, 25, 0, 0));
        VBox box = GetSideMenu();

        mainPane.setLeft(box);
        showContentOnButtonClick(stage, mainPane);

        return new Scene(mainPane, 1000, 500);
    }

//    shows context in the center based on the class its code
    private void showContentOnButtonClick(Stage stage, BorderPane mainPane) {
        this.backBtn.setOnAction(actionEvent -> stage.setScene(mainMenu.getView(stage)));

        this.topWebcasts.setOnAction(actionEvent -> {
            TopWebcastsStats topWebcasts = new TopWebcastsStats();
            mainPane.setCenter(topWebcasts.getPane());
        });

        this.averageModuleProgress.setOnAction(actionEvent -> {
            AverageModulesStats averageModulesStats = new AverageModulesStats();
            mainPane.setCenter(averageModulesStats.getPane());
        });

        this.studentModuleProgress.setOnAction(actionEvent -> {
            StudentProgressModuleStats studentProgressModuleStats = new StudentProgressModuleStats();
            mainPane.setCenter(studentProgressModuleStats.getPane());
        });

        this.courseCompleteAmount.setOnAction(actionEvent -> {
            CourseCompletedStats courseCompletedStats = new CourseCompletedStats();
            mainPane.setCenter(courseCompletedStats.getPane());
        });
    }

//    returns a new vbox
    private VBox GetSideMenu() {
        VBox box = new VBox();
        vBoxStyling(box);
        createButtons(box);
        return box;
    }

//    adds all buttons to a vbox and sets styling for all buttons
    private void createButtons(VBox box ) {
        box.getChildren().setAll(this.topWebcasts, this.averageModuleProgress, this.studentModuleProgress, this.courseCompleteAmount, this.backBtn);
        setButtonStyling(topWebcasts);
        setButtonStyling(averageModuleProgress);
        setButtonStyling(studentModuleProgress);
        setButtonStyling(courseCompleteAmount);
        setButtonStyling(backBtn);
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
        button.setMinWidth(260);
    }

//    sets the design for the vbox
    private void vBoxStyling(VBox box) {
        box.setStyle("-fx-background-color: #FAF3DC;");
        box.setPadding(new Insets(25, 25, 25, 25));
        box.setPrefWidth(300);
        box.setSpacing(30);
    }
}
