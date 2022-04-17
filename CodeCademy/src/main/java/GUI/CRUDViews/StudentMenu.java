package GUI.CRUDViews;

import Domain.Gender;
import Domain.Student;
import Database.StudentDAO;

import GUI.MainMenu;
import InputVerification.DateTools;
import InputVerification.MailTools;
import InputVerification.PostalCode;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


import java.time.LocalDate;
import java.util.ArrayList;


public class StudentMenu {
    private final StudentDAO studentDAO = new StudentDAO();
    private final MainMenu mainMenu = new MainMenu();

    private final TableView<Student> studentTable = new TableView<>();
    private final TableColumn<Student, String> emailCol = new TableColumn<>("Email");
    private final TableColumn<Student, String> nameCol = new TableColumn<>("Naam");
    private final TableColumn<Student, LocalDate> bdCol = new TableColumn<>("Geboortedatum");
    private final TableColumn<Student, String> genderCol = new TableColumn<>("Geslacht");
    private final TableColumn<Student, String> cityCol = new TableColumn<>("Woonplaats");
    private final TableColumn<Student, String> pcCol = new TableColumn<>("Postcode");
    private final TableColumn<Student, String> streetCol = new TableColumn<>("Straatnaam");
    private final TableColumn<Student, Integer> houseNrCol = new TableColumn<>("Huisnummer");
    private final TableColumn<Student, String> countryCol = new TableColumn<>("Land");

    private final Label emailLabel = new Label("Email: ");
    private final TextField tfEmail = new TextField();

    private final Label nameLabel = new Label("Naam: ");
    private final TextField tfName = new TextField();

    private final Label birthDateLabel = new Label("GeboorteDatum dd/mm/jjjj: ");
    private final TextField tfBirthMonth = new TextField();
    private final TextField tfBirthDay = new TextField();
    private final TextField tfBirthYear = new TextField();

    private final Label genderLabel = new Label("Geslacht: ");
    private final ComboBox genderMenuBox = new ComboBox();

    private final Label cityLabel = new Label("woonplaats: ");
    private final TextField tfCity = new TextField();

    private final Label pcLabel = new Label("Postcode: ");
    private final TextField tfPostalCode = new TextField();

    private final Label streetLabel = new Label("Straatnaam: ");
    private final TextField tfStreet = new TextField();

    private final Label houseNrLabel = new Label("Huisnummer: ");
    private final TextField tfhouseNr = new TextField();

    private final Label countryLabel = new Label("Land: ");
    private final TextField tfCountry = new TextField();

    private final Button btnInsert = new Button("Toevoegen");
    private final Button btnDelete = new Button("Verwijderen");
    private final Button btnUpdate = new Button("Update");
    private final Button btnSearch = new Button("Zoeken");
    private final Button btnRefresh = new Button("Refresh");
    public Button btnBack = new Button("Terug");
    private String originalEmail;


    // methode maakt GUI voor cursisten
    public Scene getView(Stage stage) {
        //  Borderpane with styling (parent)
        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: #FAF3DC;");
        bp.setPadding(new Insets(25, 25, 25, 25));

        //gridpane with styling (child)
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        //Placing table and gridpane in parent.
        bp.setRight(this.getTableViewWithData());
        bp.setLeft(gridPane);

        //Creating hbox with styling and adding crud-buttons
        HBox hboxBtns1 = new HBox();
        hboxBtns1.getChildren().setAll(this.btnInsert, this.btnUpdate, this.btnDelete);
        hboxBtns1.setSpacing(25);
        hboxBtns1.setPadding(new Insets(20, 0, 0, 0));

        //Creating hbox with styling and adding crud-buttons
        HBox hboxBtns2 = new HBox();
        hboxBtns2.getChildren().setAll(this.btnBack, this.btnSearch, this.btnRefresh);
        hboxBtns2.setSpacing(25);
        hboxBtns2.setPadding(new Insets(20, 0, 0, 0));

        //Adding date inputfields to hbox
        HBox hboxBDate = new HBox();
        hboxBDate.getChildren().setAll(tfBirthDay, tfBirthMonth, tfBirthYear);

        //Set onclick event for buttons
        btnInsert.setOnAction(actionEvent -> this.insertStudentToDatabase());
        btnUpdate.setOnAction(actionEvent -> this.updateStudent());
        btnSearch.setOnAction(actionEvent -> this.findStudent());
        btnRefresh.setOnAction(actionEvent -> this.refreshTable());
        btnDelete.setOnAction(actionEvent -> this.deleteStudent());
        btnBack.setOnAction(actionEvent -> stage.setScene(mainMenu.getView(stage)));

        setCellValueFromTableToTextField();


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
        gridPane.add(hboxBtns1, 0, 9);
        gridPane.add(hboxBtns2, 0, 10);

        //Creating scene and returning it
        Scene scene = new Scene(bp);
        return scene;
    }

    //Method (for the insert button) to create a new student in the database
    private void insertStudentToDatabase() {
        Student student = createStudentObjectFromTextFields();
        if (studentDAO.createStudent(student)) {
            confirmationMessage("Student toegevoegd.");
        } else {
            warningMessage("Toevoegen van student mislukt.");
        }
        refreshTable();
    }

    //method (for the update button) to update a student in the database
    private void updateStudent() {
        Student student = createStudentObjectFromTextFields();
        if (student != null) {
            if (studentDAO.updateStudent(student, originalEmail)) {
                confirmationMessage("Update uitgevoerd");
            }
            refreshTable();
        }
    }

    //method (for the search button) to find a student in the database
    private void findStudent() {
        ArrayList<Student> foundStudents = studentDAO.findStudent(tfEmail.getText());
        if (foundStudents.size() != 0) {
            this.studentTable.getItems().clear();
            for (Student student : foundStudents) {
                this.studentTable.getItems().add(student);
            }
        }

    }

    //    Deletes a record in the table with the given data that is needed to delete the record.
    private void deleteStudent() {
        if (studentDAO.deleteStudent(tfEmail.getText())) {
            confirmationMessage("Student verwijderd");
        } else {
            warningMessage("Verwijderen mislukt");
        }
        refreshTable();
    }


    //Method to create a student object. This method also uses the mail, date and postalcode tests to make sure the provided data is correct.
    private Student createStudentObjectFromTextFields() {
        LocalDate date;
        int day = Integer.parseInt(tfBirthDay.getText());
        int month = Integer.parseInt(tfBirthMonth.getText());
        int year = Integer.parseInt(tfBirthYear.getText());
        String postalcode;

        boolean dateBoolean = DateTools.validateDate(day, month, year);
        if (!dateBoolean) {
            warningMessage("Ongeldige geboortedatum");
            return null;
        }
        boolean mailBoolean = MailTools.validateMailAddress(tfEmail.getText());
        if (!mailBoolean) {
            warningMessage("Ongeldig mail adres.");
            return null;
        }
        try {
            PostalCode.formatPostalCode(tfPostalCode.getText());
            postalcode = PostalCode.formatPostalCode(tfPostalCode.getText());
        } catch (Exception e) {
            warningMessage("ongeldige postcode ingevoerd.");
            e.printStackTrace();
            return null;
        }

        date = LocalDate.of(year, month, day);
        Student student = new Student(
                tfEmail.getText(),
                tfName.getText(),
                date,
                Gender.valueToGenderEnum(genderMenuBox.getValue().toString()),
                tfCity.getText(),
                postalcode,
                tfStreet.getText(),
                Integer.parseInt(tfhouseNr.getText()),
                tfCountry.getText());
        return student;
    }


    //Method which refreshes the table's content.
    private void refreshTable() {
        this.studentTable.getItems().clear();
        ArrayList<Student> studentList = studentDAO.getStudentList();
        for (Student student : studentList) {
            this.studentTable.getItems().add(student);
        }
    }


    //Method which returns the student tableview
    public TableView<Student> getTableViewWithData() {
        this.studentTable.getColumns().clear();
        this.studentTable.getColumns().addAll(
                this.emailCol,
                this.nameCol,
                this.bdCol,
                this.genderCol,
                this.cityCol,
                this.pcCol,
                this.streetCol,
                this.houseNrCol,
                this.countryCol);
        setStudentDataIntoTable(studentDAO.getStudentList());
        return studentTable;
    }


    //This method places the student data into the table.
    public void setStudentDataIntoTable(ArrayList<Student> studentList) {
        this.emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
        this.nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        this.genderCol.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        this.bdCol.setCellValueFactory(new PropertyValueFactory<>("Birthdate"));
        this.cityCol.setCellValueFactory(new PropertyValueFactory<>("City"));
        this.pcCol.setCellValueFactory(new PropertyValueFactory<>("PostalCode"));
        this.streetCol.setCellValueFactory(new PropertyValueFactory<>("Street"));
        this.houseNrCol.setCellValueFactory(new PropertyValueFactory<>("HouseNr"));
        this.countryCol.setCellValueFactory(new PropertyValueFactory<>("Country"));
        for (Student student : studentList) {
            this.studentTable.getItems().add(student);
        }
    }

    //This method fills in the text fields when a cell from the table is clicked.
    private void setCellValueFromTableToTextField() {
        try {
            studentTable.setOnMouseClicked(e -> {
                Student student = studentTable.getItems().get(studentTable.getSelectionModel().getSelectedIndex());
                this.tfEmail.setText(student.getEmail());
                this.tfName.setText(student.getName());
                this.tfBirthDay.setText(String.valueOf(student.getBirthDay()));
                this.tfBirthMonth.setText(String.valueOf(student.getBirthMonth()));
                this.tfBirthYear.setText(String.valueOf(student.getBirthYear()));
                this.genderMenuBox.setValue(student.getGender());
                this.tfCity.setText(student.getCity());
                this.tfPostalCode.setText(student.getPostalCode());
                this.tfStreet.setText(student.getStreet());
                this.tfhouseNr.setText(String.valueOf(student.getHouseNr()));
                this.tfCountry.setText(student.getCountry());
                //Line below saves the original email into a variable which enables to alter the email (because the old email is needed for the WHERE clause in the update query).
                this.originalEmail = student.getEmail();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //this method shows a confirmation alert
    private static void confirmationMessage(String errorMsg) {
        Alert message = new Alert(Alert.AlertType.CONFIRMATION);
        message.setContentText(errorMsg);
        message.show();
    }

    //this method shows a warning alert
    private static void warningMessage(String errorMsg) {
        Alert message = new Alert(Alert.AlertType.WARNING);
        message.setContentText(errorMsg);
        message.show();
    }
}


