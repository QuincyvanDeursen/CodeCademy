package GUI.StatisticViews;

import GUI.MainMenu;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class StatisticsMenu {

    private final MainMenu mainMenu = new MainMenu();

    private final Button topWebcasts = new Button("Top 3 Webcasts");
    private final Button averageModuleProgress = new Button("Module Voortgang");
    private final Button studentModuleProgress = new Button("Student Voortgang");
    private final Button courseCompleteAmount = new Button("Behaalde Cursussen");
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
            Top3WebcastsView topWebcasts = new Top3WebcastsView();
            mainPane.setCenter(topWebcasts.getPane());
        });

        this.averageModuleProgress.setOnAction(actionEvent -> {
            AverageModulesView averageModulesStats = new AverageModulesView();
            mainPane.setCenter(averageModulesStats.getPane());
        });

        this.studentModuleProgress.setOnAction(actionEvent -> {
            StudentProgressModuleView studentProgressModuleStats = new StudentProgressModuleView();
            mainPane.setCenter(studentProgressModuleStats.getPane());
        });

        this.courseCompleteAmount.setOnAction(actionEvent -> {
            CourseCompletedView courseCompletedStats = new CourseCompletedView();
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
    private void createButtons(VBox box) {
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
