package com.carrot.catatrack.controller;

import com.carrot.catatrack.model.Choices;
import com.carrot.catatrack.model.Eye;
import com.carrot.catatrack.model.Patient;
import com.carrot.catatrack.model.PatientPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;

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
    private ChoiceBox chLensFilter;
    @FXML
    private ChoiceBox chStatusFilter;
    @FXML
    private ChoiceBox chVAFilter;
    @FXML
    private ChoiceBox ch;
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


    private void addPatientToAccordion(Patient patient, Eye rightEye, Eye leftEye) {
        PatientPane pane = new PatientPane(patient, rightEye, leftEye);
        patientAccordion.getPanes().add(pane);
    }

    @javafx.fxml.FXML
    public void initialize() {
        lblSurgDate.setId("Response");
        lblDOB.setId("Response");
        for (int i=0; i<3; i++) {
            addPatientToAccordion(new Patient("0304095255085","Mathee","P",Date.valueOf(LocalDate.now()), "Awaiting 2nd Surgery", "0609054561", "123")
                    , new Eye(1,'R',"mature","6/12","","","","bloem",Date.valueOf(LocalDate.now()),"sics","notes")
                    , new Eye(1,'L',"early","LP","6/6","","","kwakwa",Date.valueOf(LocalDate.now()),"sics","notes"));
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
    }

    @FXML
    public void doGeneralSearch(ActionEvent actionEvent) {
    }
}