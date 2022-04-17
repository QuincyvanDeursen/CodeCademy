package GUI.StatisticViews;

import Database.ContentItemDAO;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class Top3WebcastsView {
    private final ContentItemDAO contentItemDAO = new ContentItemDAO();
    private final Text title = new Text("Top 3 Webcasts");
    private final Text firstPlace = new Text("1. ");
    private final Text secondPlace = new Text("2. ");
    private final Text thirdPlace = new Text("3. ");

    //    Returns a borderpane that shows the whole content that is made within this class.
    public BorderPane getPane() {
        BorderPane mainPane = new BorderPane();
        mainPane.setPadding(new Insets(100, 100, 100, 100));
        title.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 2em;");
        mainPane.setTop(this.title);
        mainPane.setCenter(getTexts());
        applyTopThreeToText();
        return mainPane;
    }

    //    Returns a grid pane that decides the design on the content that is shows.
    private Node getTexts() {
        GridPane textPane = new GridPane();
        textPane.add(firstPlace, 0, 0);
        textPane.add(secondPlace, 0, 1);
        textPane.add(thirdPlace, 0, 2);
        return textPane;
    }

    //    sets the texts witht the data that has been pulled.
    private void applyTopThreeToText() {
        ArrayList<String> topWebcastsList = contentItemDAO.getTopThreeWebcast();
        firstPlace.setText("1. " + topWebcastsList.get(0));
        secondPlace.setText("2. " + topWebcastsList.get(1));
        thirdPlace.setText("3. " + topWebcastsList.get(2));
    }
}
