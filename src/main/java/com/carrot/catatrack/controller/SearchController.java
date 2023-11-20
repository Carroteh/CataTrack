package com.carrot.catatrack.controller;

import com.carrot.catatrack.model.Choices;
import com.carrot.catatrack.model.Patient;
import com.carrot.catatrack.model.PatientPane;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

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
    private ChoiceBox chLensFilter;
    @FXML
    private ChoiceBox chStatusFilter;
    @FXML
    private ChoiceBox chVAFilter;
    @FXML
    private ChoiceBox ch;


    private void addPatientToAccordion(Patient patient) {
        PatientPane pane = new PatientPane(patient);
        patientAccordion.getPanes().add(pane);
    }

    @javafx.fxml.FXML
    public void initialize() {
        lblSurgDate.setId("Response");
        lblDOB.setId("Response");
        for (int i=0; i<3; i++) {
            addPatientToAccordion(new Patient());
        }
    }

    @javafx.fxml.FXML
    public void goToAddPatient(ActionEvent actionEvent) throws IOException {
        stage = (Stage) root.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/carrot/catatrack/addPatient-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }
}