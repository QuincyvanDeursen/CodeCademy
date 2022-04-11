package GUI;

import Database.StatisticsDAO;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class TopWebcastsStats {
    StatisticsDAO getStatsData = new StatisticsDAO();

    Text title = new Text("Top 3 Webcasts");
    Text firstPlace = new Text("1. ");
    Text secondPlace = new Text("2. ");
    Text thirdPlace = new Text("3. ");

    ArrayList<String> topWebcastsList;
    public BorderPane getPane () {

        BorderPane mainPane = new BorderPane();

        mainPane.setPadding(new Insets(100,100,100,100));

        title.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 2em;");
        mainPane.setTop(this.title);

        mainPane.setCenter(getTexts());

        applyTopThreeToText();

        return mainPane;
    }

    private Node getTexts() {
        GridPane textPane = new GridPane();
        textPane.add(firstPlace,0,0);
        textPane.add(secondPlace,0,1);
        textPane.add(thirdPlace,0,2);
        return textPane;
    }

    private void applyTopThreeToText() {
        topWebcastsList = getStatsData.getTopThreeWebcast();

        firstPlace.setText("1. " + topWebcastsList.get(0));
        secondPlace.setText("2. " + topWebcastsList.get(1));
        thirdPlace.setText("3. " + topWebcastsList.get(2));
    }
}
