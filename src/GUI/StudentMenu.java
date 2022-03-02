package GUI;

import Domain.Gender;
import Domain.Student;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.Date;

public class StudentMenu {
    private Label emailLabel = new Label("Email: ");
    private TextField tfEmail = new TextField();

    private Label nameLabel = new Label("Naam: ");
    private TextField tfName = new TextField();

    private Label birthDateLabel = new Label("GeboorteDatum d/m/j: ");


    private Label genderLabel = new Label("Geslacht: ");
    private ComboBox genderMenuBox = new ComboBox();

    private Label cityLabel = new Label("woonplaats: ");
    private TextField tfCity = new TextField();

    private Label pcLabel = new Label("Postcode: ");
    private TextField tfPostalCode = new TextField();

    private Label streetLabel = new Label("Straatnaam: ");
    private TextField tfStreet = new TextField();

    private Label houseNrLabel = new Label("Huisnummer: ");
    private TextField tfhouseNr = new TextField();

    private Label countryLabel = new Label("Land: ");
    private TextField tfCountry = new TextField();

    private Button btnInsert = new Button("Toevoegen");
    private Button btnDelete = new Button("Verwijderen");
    private Button btnUpdate = new Button("Update");
    public Button backBtn = new Button("Terug");
    private String emailCellValue;





    // methode maakt GUI voor cursisten
    public  Scene getView() {

        //  Borderpane with styling (parent)
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(25, 25, 25, 25));

        //gridpane with styling (child)
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        //Placing table and gridpane in parent.
        bp.setRight(this.getTableWithData());
        bp.setLeft(gridPane);

        //Creating hbox with styling and adding crud-buttons
        HBox hboxBtns = new HBox();
        hboxBtns.getChildren().setAll(this.backBtn, this.btnInsert, this.btnUpdate, this.btnDelete);
        hboxBtns.setSpacing(25);
        hboxBtns.setPadding(new Insets(20, 0, 0, 0));

        //Adding date inputfields to hbox
        HBox hboxBDate = new HBox();
        TextField tfBirthMonth = new TextField();
        TextField tfBirthDay = new TextField();
        TextField tfBirthYear = new TextField();
        hboxBDate.getChildren().setAll(tfBirthDay, tfBirthMonth, tfBirthYear);

        //Set onclick event for buttons
        // TODO: 2-3-2022



        // Enum geslachten ophalen en in dropdown menu zetten
        for (Gender gender : Gender.values()) {
            genderMenuBox.getItems().add(gender);
        }

        //Adding everything to gridpane.
        gridPane.add(emailLabel, 0, 0);
        gridPane.add(tfEmail, 1, 0);
        gridPane.add(nameLabel, 0, 1);
        gridPane.add(tfName, 1, 1);
        gridPane.add(birthDateLabel, 0, 2);
        gridPane.add(hboxBDate, 1, 2);
        gridPane.add(genderLabel, 0, 3);
        gridPane.add(genderMenuBox, 1, 3);
        gridPane.add(cityLabel, 0, 4);
        gridPane.add(tfCity, 1, 4);
        gridPane.add(pcLabel, 0, 5);
        gridPane.add(tfPostalCode, 1, 5);
        gridPane.add(streetLabel, 0, 6);
        gridPane.add(tfStreet, 1, 6);
        gridPane.add(houseNrLabel, 0, 7);
        gridPane.add(tfhouseNr, 1, 7);
        gridPane.add(countryLabel, 0, 8);
        gridPane.add(tfCountry, 1, 8);
        gridPane.add(hboxBtns, 0, 9);

        //Creating scene and returning it
        Scene scene = new Scene(bp);
        return scene;
    }

    //Method which returns the student tableview
    public TableView<Student> getTableWithData() {
        TableView<Student> studentTable = new TableView<>();
        TableColumn<Student, String> emailCol = new TableColumn<>("Email");
        TableColumn<Student, String> nameCol = new TableColumn<>("Naam");
        TableColumn<Student, Date> bdCol = new TableColumn<>("Geboortedatum");
        TableColumn<Student, String> genderCol = new TableColumn<>("Geslacht");
        TableColumn<Student, String> cityCol = new TableColumn<>("Woonplaats");
        TableColumn<Student, String> pcCol = new TableColumn<>("Postcode");
        TableColumn<Student, String> streetCol = new TableColumn<>("Straatnaam");
        TableColumn<Student, Integer> houseNrCol = new TableColumn<>("Huisnummer");
        TableColumn<Student, String> countryCol = new TableColumn<>("Land");

        studentTable.getColumns().clear();

        studentTable.getColumns().addAll(
                emailCol,
                nameCol,
                bdCol,
                genderCol,
                cityCol,
                pcCol,
                streetCol,
                houseNrCol,
                countryCol);

        return studentTable;
    }


}


