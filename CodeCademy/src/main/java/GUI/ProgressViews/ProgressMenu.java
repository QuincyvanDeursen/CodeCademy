package GUI.ProgressViews;

import GUI.MainMenu;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProgressMenu {
    private MainMenu mainMenu = new MainMenu();
    private Button webcastProgress = new Button("Webcast");
    private Button moduleProgress = new Button("Module");
    public Button backBtn = new Button("Terug");

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

        this.webcastProgress.setOnAction(actionEvent -> {
            WebcastProgressView webcastProgressView = new WebcastProgressView();
            mainPane.setCenter(webcastProgressView.getPane());

        });

        this.moduleProgress.setOnAction(actionEvent -> {
            ModuleProgressView moduleProgressView = new ModuleProgressView();
            mainPane.setCenter(moduleProgressView.getPane());
        });
    }

    //    returns a new vbox
    private VBox GetSideMenu() {
        VBox box = new VBox();
        vBoxStyling(box);
        createButtonBox(box);
        return box;
    }

    //    adds all buttons to a vbox and sets styling for all buttons
    private void createButtonBox(VBox box ) {
        box.getChildren().setAll(this.moduleProgress, this.webcastProgress, this.backBtn);
        setButtonStyling(this.moduleProgress);
        setButtonStyling(this.webcastProgress);
        setButtonStyling(this.backBtn);
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
        box.setSpacing(50);
        box.setPrefWidth(300);
    }
}
