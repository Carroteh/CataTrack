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
    private DatabaseService db;
    @FXML
    private Accordion patientAccordion;
    @FXML
    private Label lblSurgDate;
    @FXML
    private ChoiceBox<String> chLensFilter;
    @FXML
    private TextField txtSurname;
    @FXML
    private TextField txtInitials;
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
    private ChoiceBox<String> chSurgTypeFilter;
    @FXML
    private MenuItem itmAddPatient;
    @FXML
    private ChoiceBox<String> chVAFilter1;
    @FXML
    private ChoiceBox<String> chVAFilter2;
    @FXML
    private ChoiceBox<String> chStatusFilter;
    @FXML
    private ChoiceBox<String> chPlaceFilter;
    @FXML
    private Label lblNoResults;
    @FXML
    private Label lblNoPatients;


    private void addPersonToAccordion(Person person) {
        PatientPane pane = new PatientPane(person);
        patientAccordion.getPanes().add(pane);
    }

    @javafx.fxml.FXML
    public void initialize() {
        //Setup choice boxes and choices
        chStatusFilter.setItems(FXCollections.observableArrayList(Choices.status));
        chLensFilter.setItems(FXCollections.observableArrayList(Choices.Lens));
        chVAFilter1.setItems(FXCollections.observableArrayList(Choices.VAList));
        chVAFilter2.setItems(FXCollections.observableArrayList(Choices.VAList));
        chSurgTypeFilter.setItems(FXCollections.observableArrayList(Choices.surgeryType));
        chPlaceFilter.setItems(FXCollections.observableArrayList(Choices.surgPlaces));

        chStatusFilter.setValue("N/A");
        chLensFilter.setValue("N/A");
        chVAFilter1.setValue("N/A");
        chVAFilter2.setValue("N/A");
        chSurgTypeFilter.setValue("N/A");
        chPlaceFilter.setValue("N/A");
        lblSurgDate.setId("Response");

        db = new DatabaseService();

        lblNoPatients.setText(db.getNumPatients() + " Patients");
    }

    @javafx.fxml.FXML
    public void goToAddPatient(ActionEvent actionEvent) throws IOException {
        //Change view to addPatientView.fxml
        Stage stage = (Stage) root.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/carrot/catatrack/views/addPatientView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    @FXML
    public void doPatientSearch(ActionEvent actionEvent) {
        clearResults();

        ArrayList<Person> results = db.patientSearch(txtSurname.getText(), txtInitials.getText(),Date.valueOf("1000-01-01"), txtID.getText(), "N/A", "", "");

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

        ArrayList<Person> results = db.generalSearch(Date.valueOf(DateUtils.getDate(dateSurgFilter)), chkMonth.isSelected(), chLensFilter.getValue(), chStatusFilter.getValue() ,chVAFilter1.getValue(), chVAFilter2.getValue(), chSurgTypeFilter.getValue(), chPlaceFilter.getValue());

        if(results != null) {
            for(Person person : results) {
                addPersonToAccordion(person);
            }
            lblNoResults.setText(results.size() + " Results");
        }
    }

    private void clearResults() {
        patientAccordion.getPanes().clear();
    }
}