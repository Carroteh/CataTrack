package com.carrot.catatrack.controller;

import com.carrot.catatrack.db.DatabaseService;
import com.carrot.catatrack.model.*;
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
    private ChoiceBox<String> chSurgType_OS;
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
    private DatePicker dateSurg_OD;
    @javafx.fxml.FXML
    private ChoiceBox<String> chSurgType_OD;
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
    private ChoiceBox<String> chSurgPlace_OD;
    @javafx.fxml.FXML
    private ChoiceBox<String> chSurgPlace_OS;
    @javafx.fxml.FXML
    private TextField txtFinalVA_OD;
    @javafx.fxml.FXML
    private TextField txtFinalVA_OS;

    @javafx.fxml.FXML
    public void initialize() {
        //Setup Choice box options
        setupVAChoiceBoxes(chInitialVA_OD, chPostopVA_OD, ch2WeekVA_OD, ch6WeekVA_OD);
        setupVAChoiceBoxes(chInitialVA_OS, chPostopVA_OS, ch2WeekVA_OS, ch6WeekVA_OS);

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

        chSurgPlace_OD.setItems(FXCollections.observableArrayList(Choices.surgPlaces));
        chSurgPlace_OD.setValue("N/A");
        chSurgPlace_OS.setItems(FXCollections.observableArrayList(Choices.surgPlaces));
        chSurgPlace_OS.setValue("N/A");

        txtFinalVA_OD.setEditable(false);
        txtFinalVA_OS.setEditable(false);
    }

    private void setupVAChoiceBoxes(ChoiceBox<String> chInitialVAOd, ChoiceBox<String> chPostopVAOd, ChoiceBox<String> ch2WeekVAOd, ChoiceBox<String> ch6WeekVAOd) {
        chInitialVAOd.setItems(FXCollections.observableArrayList(Choices.VAList));
        chInitialVAOd.setValue("N/A");
        chPostopVAOd.setItems(FXCollections.observableArrayList(Choices.VAList));
        chPostopVAOd.setValue("N/A");
        ch2WeekVAOd.setItems(FXCollections.observableArrayList(Choices.VAList));
        ch2WeekVAOd.setValue("N/A");
        ch6WeekVAOd.setItems(FXCollections.observableArrayList(Choices.VAList));
        ch6WeekVAOd.setValue("N/A");
    }

    @javafx.fxml.FXML
    public void goToSearch(ActionEvent actionEvent) throws IOException {
        //Change scene to Search view
        Stage stage = (Stage) root.getScene().getWindow();
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

        //Create and insert Right and Left eye
        LocalDate surgDateOD = DateUtils.getDate(dateSurg_OD);
        Eye rightEye = new Eye('R', chLens_OD.getValue(), chInitialVA_OD.getValue(), chPostopVA_OD.getValue(),
                ch2WeekVA_OD.getValue(), ch6WeekVA_OD.getValue(), chSurgPlace_OD.getValue(), Date.valueOf(surgDateOD),
                chSurgType_OD.getValue(),txtSurgNotes_OD.getText());


        LocalDate surgDateOS = DateUtils.getDate(dateSurg_OS);
        Eye leftEye = new Eye('L', chLens_OS.getValue(), chInitialVA_OS.getValue(), chPostopVA_OS.getValue(),
                ch2WeekVA_OS.getValue(), ch6WeekVA_OS.getValue(), chSurgPlace_OS.getValue(), Date.valueOf(surgDateOS),
                chSurgType_OS.getValue(),txtSurgNotes_OS.getText());

        Person person = new Person(patient, rightEye, leftEye);

        boolean status = db.insertPerson(person);

        if(status) {
            lblStatus.setText("Success!");
            clearFields();
        } else {
            lblStatus.setText("Something went wrong.");
        }
    }

    /**
     * Function that clears all the input fields
     */
    private void clearFields() {
        txtSurname.setText("");
        chStatus.setValue("N/A");
        txtInitials.setText("");
        txtContact.setText("");
        txtAltContact.setText("");
        txtID.setText("");
        dateBirth.setValue(null);
        dateBirth.getEditor().setText("");
        chLens_OD.setValue("N/A");
        chInitialVA_OD.setValue("N/A");
        chPostopVA_OD.setValue("N/A");
        ch2WeekVA_OD.setValue("N/A");
        ch6WeekVA_OD.setValue("N/A");

        dateSurg_OD.setValue(null);
        dateSurg_OD.getEditor().setText("");
        chSurgType_OD.setValue("N/A");
        chSurgPlace_OD.setValue("N/A");
        txtSurgNotes_OD.setText("");

        chLens_OS.setValue("N/A");
        chInitialVA_OS.setValue("N/A");
        chPostopVA_OS.setValue("N/A");
        ch2WeekVA_OS.setValue("N/A");
        ch6WeekVA_OS.setValue("N/A");
        dateSurg_OS.setValue(null);
        dateSurg_OS.getEditor().setText("");
        chSurgType_OS.setValue("N/A");
        chSurgPlace_OS.setValue("N/A");
        txtSurgNotes_OS.setText("");
    }
}