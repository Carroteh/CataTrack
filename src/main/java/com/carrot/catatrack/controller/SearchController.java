package com.carrot.catatrack.controller;

import com.carrot.catatrack.db.DatabaseService;
import com.carrot.catatrack.model.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
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
    private DatabaseService db;
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
    private ChoiceBox<String> chVAFilter;
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
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtAltContact;
    @FXML
    private ChoiceBox<String> chSurgTypeFilter;
    @FXML
    private TextField txtSurgPlaceFilter;


    private void addPersonToAccordion(Person person) {
        PatientPane pane = new PatientPane(person);
        patientAccordion.getPanes().add(pane);
    }

    @javafx.fxml.FXML
    public void initialize() {
        //Setup choice boxes and choices
        chStatus.setItems(FXCollections.observableArrayList(Choices.status));
        chStatus.setValue("N/A");
        chLensFilter.setItems(FXCollections.observableArrayList(Choices.Lens));
        chVAFilter.setItems(FXCollections.observableArrayList(Choices.VAList));
        chSurgTypeFilter.setItems(FXCollections.observableArrayList(Choices.surgeryType));
        chLensFilter.setValue("N/A");
        chVAFilter.setValue("N/A");
        chSurgTypeFilter.setValue("N/A");

        db = new DatabaseService();

        lblDOB.setId("Response");
        lblSurgDate.setId("Response");
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
        clearResults();

        ArrayList<Person> results = db.patientSearch(txtSurname.getText(), txtInitials.getText(), Date.valueOf(DateUtils.getDate(dateBirth)), txtID.getText(), chStatus.getValue(), txtContact.getText(), txtAltContact.getText());

        //Add the found patients to the list
        if(results != null) {
            for (Person person : results) {
                addPersonToAccordion(person);
            }
        }
        clearDatePicker();
    }

    @FXML
    public void doGeneralSearch(ActionEvent actionEvent) {
        clearResults();

        ArrayList<Person> results = db.generalSearch(Date.valueOf(DateUtils.getDate(dateSurgFilter)), chkMonth.isSelected(), chLensFilter.getValue(), chVAFilter.getValue(), chSurgTypeFilter.getValue(), txtSurgPlaceFilter.getText());

        if(results != null) {
            for(Person person : results) {
                addPersonToAccordion(person);
            }
        }
        clearDatePicker();
    }

    private void clearResults() {
        patientAccordion.getPanes().clear();
    }

    private void clearDatePicker() {

    }
}