package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainMenu extends Application {
    private static Label crudLabel = new Label("CRUD");

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Thomas van Otterloo (2186082) / Quincy van Deursen (2113709)");
        stage.setScene(getView(stage));
        stage.show();
    }


    //    Creating a pane for the scene.
    private Scene getView(Stage window) {
        GridPane mainPane = getMainPane(window);
        Scene scene = new Scene(mainPane, 950, 300);
        return scene;
    }

    //    creating Buttons inside the pane.
    private GridPane getMainPane(Stage window) {
        GridPane mainPane = new GridPane();

        Button studentButton = new Button("Cursisten");
        Button cursusButton = new Button("Cursussen");
        Button enrollButton = new Button("Inschrijvingen");
        Button certificateButton = new Button("Certificaten");

        mainPane.add(crudLabel, 0, 0);
        mainPane.add(studentButton, 0, 1);
        mainPane.add(cursusButton, 1, 1);
        mainPane.add(enrollButton, 2, 1);
        mainPane.add(certificateButton, 3, 1);

        createActionsForButtons(studentButton, cursusButton, enrollButton, certificateButton, window);

        setStyling(mainPane, studentButton, cursusButton, enrollButton, certificateButton);

        return mainPane;
    }

    // Creates styles for everything.
    private void setStyling(GridPane mainPane, Button studentButton, Button cursusButton, Button enrollButton, Button certificateButton) {
        mainPane.setPadding(new Insets(25, 25, 25, 25));
        mainPane.setVgap(50);
        mainPane.setHgap(40);
        mainPane.setStyle("-fx-background-color:#FAF3DC");

        crudLabel.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 2em;");

        studentButton.setStyle(
                "-fx-background-color:#2596be;" +
                        "-fx-font-size: 2em;" +
                        " -fx-background-radius: 12;" +
                        "-fx-effect: dropshadow( gaussian , rgba(0,0,0,5) , 4,0,0,2 );" +
                        "-fx-font-weight: bold;"
        );

        cursusButton.setStyle(
                "-fx-background-color:#2596be;" +
                        "-fx-font-size: 2em;" +
                        " -fx-background-radius: 12;" +
                        "-fx-effect: dropshadow( gaussian , rgba(0,0,0,5) , 4,0,0,2 );" +
                        "-fx-font-weight: bold;"
        );

        enrollButton.setStyle(
                "-fx-background-color:#2596be;" +
                        "-fx-font-size: 2em;" +
                        " -fx-background-radius: 12;" +
                        "-fx-effect: dropshadow( gaussian , rgba(0,0,0,5) , 4,0,0,2 );" +
                        "-fx-font-weight: bold;"
        );

        certificateButton.setStyle(
                "-fx-background-color:#2596be;" +
                        "-fx-font-size: 2em;" +
                        " -fx-background-radius: 12;" +
                        "-fx-effect: dropshadow( gaussian , rgba(0,0,0,5) , 4,0,0,2 );" +
                        "-fx-font-weight: bold;"
        );
    }

    // Handles all SetOnAction buttons.
    private void createActionsForButtons(Button studentButton, Button cursusButton, Button enrollButton, Button certificateButton, Stage window) {
        StudentMenu studentMenu = new StudentMenu();
        //Opens student menu
        studentButton.setOnAction(actionEvent -> {
            window.setScene(studentMenu.getView());
        });
        //returns to main menu
        studentMenu.backBtn.setOnAction(actionEvent -> {window.setScene(this.getView(window));
        });




        cursusButton.setOnAction(actionEvent -> {
//                    window.setScene(naamClassinvullen.getVieuw());
        });

        enrollButton.setOnAction(actionEvent -> {
//                    window.setScene(EnrollmentMenu.getVieuw());
        });

        certificateButton.setOnAction(actionEvent -> {
//                    window.setScene(naamClassinvullen.getVieuw());
        });
    }


}
