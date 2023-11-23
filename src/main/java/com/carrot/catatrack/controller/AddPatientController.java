package com.carrot.catatrack.controller;

import com.carrot.catatrack.db.DatabaseService;
import com.carrot.catatrack.model.Choices;
import com.carrot.catatrack.model.DateUtils;
import com.carrot.catatrack.model.Eye;
import com.carrot.catatrack.model.Patient;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Philip Mathee
 * @version 1.0
 * Add Patient controller
 */
public class AddPatientController implements Choices
{
    @javafx.fxml.FXML
    private AnchorPane root;
    @javafx.fxml.FXML
    private MenuItem itmSearch;
    private Stage stage;
    @javafx.fxml.FXML
    private TextField txtSurname;
    @javafx.fxml.FXML
    private TextField txtInitials;
    @javafx.fxml.FXML
    private TextField txtID;
    @javafx.fxml.FXML
    private DatePicker dateBirth;
    @javafx.fxml.FXML
    private ChoiceBox<String> chStatus;
    @javafx.fxml.FXML
    private Button btnAdd;
    @javafx.fxml.FXML
    private ChoiceBox<String> chInitialVA_OS;
    @javafx.fxml.FXML
    private ChoiceBox<String> chPostopVA_OS;
    @javafx.fxml.FXML
    private ChoiceBox<String> ch2WeekVA_OS;
    @javafx.fxml.FXML
    private ChoiceBox<String> ch6WeekVA_OS;
    @javafx.fxml.FXML
    private ChoiceBox<String> chFinalVA_OS;
    @javafx.fxml.FXML
    private ChoiceBox<String> chSurgType_OS;
    @javafx.fxml.FXML
    private TextField txtSurgPlace_OS;
    @javafx.fxml.FXML
    private TextArea txtSurgNotes_OS;
    @javafx.fxml.FXML
    private ChoiceBox<String> chInitialVA_OD;
    @javafx.fxml.FXML
    private ChoiceBox<String> chPostopVA_OD;
    @javafx.fxml.FXML
    private ChoiceBox<String> ch2WeekVA_OD;
    @javafx.fxml.FXML
    private ChoiceBox<String> ch6WeekVA_OD;
    @javafx.fxml.FXML
    private ChoiceBox<String> chFinalVA_OD;
    @javafx.fxml.FXML
    private DatePicker dateSurg_OD;
    @javafx.fxml.FXML
    private ChoiceBox<String> chSurgType_OD;
    @javafx.fxml.FXML
    private TextField txtSurgPlace_OD;
    @javafx.fxml.FXML
    private TextArea txtSurgNotes_OD;
    @javafx.fxml.FXML
    private TextField txtContact;
    @javafx.fxml.FXML
    private TextField txtAltContact;
    @javafx.fxml.FXML
    private ChoiceBox<String> chLens_OD;
    @javafx.fxml.FXML
    private ChoiceBox<String> chLens_OS;
    @javafx.fxml.FXML
    private DatePicker dateSurg_OS;
    @javafx.fxml.FXML
    private Label lblStatus;

    @javafx.fxml.FXML
    public void initialize() {
        //Setup Choice box options
        setupVAChoiceBoxes(chInitialVA_OD, chPostopVA_OD, ch2WeekVA_OD, ch6WeekVA_OD, chFinalVA_OD);
        setupVAChoiceBoxes(chInitialVA_OS, chPostopVA_OS, ch2WeekVA_OS, ch6WeekVA_OS, chFinalVA_OS);

        chStatus.setItems(FXCollections.observableArrayList(Choices.status));
        chStatus.setValue("N/A");

        chSurgType_OD.setItems(FXCollections.observableArrayList(Choices.surgeryType));
        chSurgType_OD.setValue("N/A");
        chSurgType_OS.setItems(FXCollections.observableArrayList(Choices.surgeryType));
        chSurgType_OS.setValue("N/A");

        chLens_OD.setItems(FXCollections.observableArrayList(Choices.Lens));
        chLens_OD.setValue("N/A");
        chLens_OS.setItems(FXCollections.observableArrayList(Choices.Lens));
        chLens_OS.setValue("N/A");
    }

    private void setupVAChoiceBoxes(ChoiceBox<String> chInitialVAOd, ChoiceBox<String> chPostopVAOd, ChoiceBox<String> ch2WeekVAOd, ChoiceBox<String> ch6WeekVAOd, ChoiceBox<String> chFinalVAOd) {
        chInitialVAOd.setItems(FXCollections.observableArrayList(Choices.VAList));
        chInitialVAOd.setValue("N/A");
        chPostopVAOd.setItems(FXCollections.observableArrayList(Choices.VAList));
        chPostopVAOd.setValue("N/A");
        ch2WeekVAOd.setItems(FXCollections.observableArrayList(Choices.VAList));
        ch2WeekVAOd.setValue("N/A");
        ch6WeekVAOd.setItems(FXCollections.observableArrayList(Choices.VAList));
        ch6WeekVAOd.setValue("N/A");
        chFinalVAOd.setItems(FXCollections.observableArrayList(Choices.VAList));
        chFinalVAOd.setValue("N/A");
    }

    @javafx.fxml.FXML
    public void goToSearch(ActionEvent actionEvent) throws IOException {
        //Change scene to Search view
        stage = (Stage) root.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/carrot/catatrack/views/searchView.fxml"));
        URL stylesheet = getClass().getResource("/com/carrot/catatrack/styles/styles.css");
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(String.valueOf(stylesheet));
        stage.setScene(scene);
    }

    @javafx.fxml.FXML
    public void AddPatient(ActionEvent actionEvent) {
        DatabaseService db = new DatabaseService();

        //Create new Patient
        LocalDate birthDate = DateUtils.getDate(dateBirth);

        Patient patient = new Patient(txtID.getText(), txtSurname.getText(), txtInitials.getText(),
                                        Date.valueOf(birthDate), chStatus.getValue(), txtContact.getText(),
                                        txtAltContact.getText());

        //Insert patient into Database
        int patientID = db.insertPatient(patient);

        if(patientID != -1) {

            //Create and insert Right and Left eye
            LocalDate surgDateOD = DateUtils.getDate(dateSurg_OD);
            Eye rightEye = new Eye(patientID, 'R', chLens_OD.getValue(), chInitialVA_OD.getValue(), chPostopVA_OD.getValue(),
                    ch2WeekVA_OD.getValue(), ch6WeekVA_OD.getValue(), txtSurgPlace_OD.getText(), Date.valueOf(surgDateOD),
                    chSurgType_OD.getValue(),txtSurgNotes_OD.getText());

            int rCode = db.insertEye(rightEye);

            LocalDate surgDateOS = DateUtils.getDate(dateSurg_OS);
            Eye leftEye = new Eye(patientID, 'L', chLens_OS.getValue(), chInitialVA_OS.getValue(), chPostopVA_OS.getValue(),
                    ch2WeekVA_OS.getValue(), ch6WeekVA_OS.getValue(), txtSurgPlace_OS.getText(), Date.valueOf(surgDateOS),
                    chSurgType_OS.getValue(),txtSurgNotes_OS.getText());


            int lCode = db.insertEye(leftEye);

            //Display useful response message
            if(rCode == -1) {
                lblStatus.setText("Error Right Eye.");
            }
            else if(lCode == -1) {
                lblStatus.setText("Error Left Eye.");
            }
            else {
                lblStatus.setText("Success!");
            }
        }
        else {
            lblStatus.setText("Something went wrong.");
        }
    }
}