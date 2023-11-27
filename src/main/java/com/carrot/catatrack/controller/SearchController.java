package com.carrot.catatrack.controller;

import com.carrot.catatrack.db.DatabaseService;
import com.carrot.catatrack.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Philip Mathee
 * @version 1.0
 * Search View Controller
 */
public class SearchController implements Choices
{
    @javafx.fxml.FXML
    private AnchorPane root;
    @javafx.fxml.FXML
    private MenuItem itmAddPatient;
    private Stage stage;
    @FXML
    private Accordion patientAccordion;
    @FXML
    private Label lblSurgDate;
    @FXML
    private Label lblDOB;
    @FXML
    private ChoiceBox<String> chLensFilter;
    @FXML
    private ChoiceBox<String> chStatusFilter;
    @FXML
    private ChoiceBox<String> chVAFilter;
    @FXML
    private ChoiceBox<String> ch;
    @FXML
    private TextField txtSurname;
    @FXML
    private TextField txtInitials;
    @FXML
    private DatePicker dateBirth;
    @FXML
    private TextField txtID;
    @FXML
    private Button btnPatientSearch;
    @FXML
    private CheckBox chkMonth;
    @FXML
    private DatePicker dateSurgFilter;
    @FXML
    private Button btnGeneralSearch;
    @FXML
    private ChoiceBox<String> chStatus;


    private void addPersonToAccordion(Person person) {
        PatientPane pane = new PatientPane(person);
        patientAccordion.getPanes().add(pane);
    }

    @javafx.fxml.FXML
    public void initialize() {
        chStatus.setItems(FXCollections.observableArrayList(Choices.status));
        chStatus.setValue("N/A");
        lblSurgDate.setId("Response");
        lblDOB.setId("Response");
        for (int i=0; i<3; i++) {
            addPersonToAccordion(new Person(new Patient("0304095255085","Mathee","P",Date.valueOf(LocalDate.now()), "Awaiting 2nd Surgery", "0609054561", "123")
                    , new Eye(1,'R',"mature","6/12","","","","bloem",Date.valueOf(LocalDate.now()),"sics","notes")
                    , new Eye(1,'L',"early","LP","6/6","","","kwakwa",Date.valueOf(LocalDate.now()),"sics","notes")));
        }
    }

    @javafx.fxml.FXML
    public void goToAddPatient(ActionEvent actionEvent) throws IOException {
        //Change view to addPatientView.fxml
        stage = (Stage) root.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/carrot/catatrack/views/addPatientView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    @FXML
    public void doPatientSearch(ActionEvent actionEvent) {
        patientAccordion.getPanes().clear();

        DatabaseService db = new DatabaseService();

        ArrayList<Person> results = db.patientSearch(txtSurname.getText(), txtInitials.getText(), Date.valueOf(DateUtils.getDate(dateBirth)), txtID.getText(), chStatus.getValue());
        System.out.println(txtSurname.getText());
        System.out.println(txtInitials.getText());
        System.out.println(Date.valueOf(DateUtils.getDate(dateBirth)));
        System.out.println(chStatus.getValue());
        //Add the found patients to the list
        if(results != null) {
            for (Person person : results) {
                addPersonToAccordion(person);
            }
        }
    }

    @FXML
    public void doGeneralSearch(ActionEvent actionEvent) {
        clearResults();
    }

    private void clearResults() {

    }
}