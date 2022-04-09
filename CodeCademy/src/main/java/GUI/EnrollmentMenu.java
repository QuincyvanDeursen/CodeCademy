package GUI;

import Database.EnrollDAO;
import Domain.Enrollment;
import Domain.Student;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class EnrollmentMenu {


    MainMenu menu = new MainMenu();
    EnrollDAO enrollDAO = new EnrollDAO();

    public Button backBtn = new Button("Terug");
    private final Button enrollBtn = new Button("Inschrijven");
    private final Label introText = new Label("Schrijf je in!");

    private final Label mailText = new Label("* email: ");
    private final TextField mailTextField = new TextField();
    private final Label errorMail = new Label("");

    private Label courseName = new Label("* cursus: ");
    private ComboBox comboBoxCourse = new ComboBox();
    private ComboBox comboBoxMail = new ComboBox();
    private final Label errorCourse = new Label("");



    private Button btnInsert = new Button("Toevoegen");
    private Button btnDelete = new Button("Verwijderen");
    private Button btnUpdate = new Button("Update");



    String emailCellValue = "";
    String courseCellValue = "";

    private TableView<Enrollment> enrollTable = new TableView<>();
    private TableColumn<Enrollment, String> emailCol = new TableColumn<>("Email");
    private TableColumn<Enrollment, LocalDate> dateCol = new TableColumn<>("Date");
    private TableColumn<Enrollment, String> courseCol = new TableColumn<>("CourseName");



    public Scene getView(Stage stage) {
        BorderPane mainPane = new BorderPane();
        GridPane userInputPane = getContent();
        GridPane buttonsPane = getButtons(stage);

        mainPane.setCenter(userInputPane);
        mainPane.setBottom(buttonsPane);
        mainPane.setRight(getTableWithData());
        Scene scene = new Scene(mainPane,900,400);
        return scene;
    }


    private GridPane getButtons(Stage stage) {
        GridPane pane = new GridPane();

        pane.add(backBtn,0,0);
        backBtn.setOnAction(actionEvent -> stage.setScene(menu.getView(stage)));
        pane.add(btnInsert,1,0);
        pane.add(btnUpdate,2,0);
        pane.add(btnDelete,3,0);

        pane.setPadding(new Insets(50,50,50,50));
        pane.setVgap(30);
        pane.setHgap(30);
        return pane;
    }

    //Method which returns the Enrollment tableview
    public <Enrollment> TableView<Enrollment> getTableWithData() {
        TableView<Enrollment> studentTable = new TableView<>();
        TableColumn<Enrollment, String> emailCol = new TableColumn<>("Email");
        TableColumn<Enrollment, Date> dateCol = new TableColumn<>("InschrijfDatum");
        TableColumn<Enrollment, String> cursusCol = new TableColumn<>("Cursus");
//        TableColumn<Enrollment, String> idCol = new TableColumn<>("CertificaarID");

        studentTable.getColumns().clear();
        studentTable.getColumns().addAll(
                emailCol,
                dateCol,
                cursusCol
//                idCol
        );

        showEnrolls();

        return studentTable;
    }

    public void showEnrolls(){
        ArrayList<Enrollment> enrollList = enrollDAO.getStudentList();
        this.emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
        this.dateCol.setCellValueFactory(new PropertyValueFactory<>("RegistrationDate"));
        this.courseCol.setCellValueFactory(new PropertyValueFactory<>("CourseName"));

        for (Enrollment enrollment: enrollList) {
            this.enrollTable.getItems().add(enrollment);
        }

    }

    //    De code die de exceptie gebruikt om te kijken of er duplicaties zitten in het tabel.
    private boolean isDuplicateEntryException(SQLException exc) {
        return exc.getSQLState().equals("23000");
    }



    private GridPane getContent() {
        GridPane gridPane = new GridPane();
        gridPane.add(introText,0,0);
        introText.setFont(Font.font("Verdana",15));

        gridPane.add(mailText,0,1);
        gridPane.add(comboBoxMail,1,1);
        comboBoxMail.setPrefWidth(150);
        comboBoxMail.getItems().clear();

        gridPane.add(errorMail,2,1);
        gridPane.add(courseName,0,2);
        gridPane.add(comboBoxCourse,1,2);
        gridPane.add(errorCourse,2,2);
        comboBoxCourse.setPrefWidth(150);
        comboBoxCourse.getItems().clear();


        gridPane.setPadding(new Insets(50,50,50,50));
        gridPane.setVgap(30);
        gridPane.setHgap(30);
        return gridPane;
    }

    public void showDuplicationError () {
        Alert warningAlert = new Alert(Alert.AlertType.WARNING);
        warningAlert.setContentText("Er bestaat al een record met deze combinatie!");
        warningAlert.show();
    }
}
