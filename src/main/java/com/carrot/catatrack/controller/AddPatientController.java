package com.carrot.catatrack.controller;

import com.carrot.catatrack.model.Choices;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

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
    private DatePicker dateSurgOS;
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
    private ChoiceBox chLens_OD;
    @javafx.fxml.FXML
    private ChoiceBox chLens_OS;

    @javafx.fxml.FXML
    public void initialize() {
        //Setup choice boxes
        setupVAChoiceBoxes(chInitialVA_OD, chPostopVA_OD, ch2WeekVA_OD, ch6WeekVA_OD, chFinalVA_OD);
        setupVAChoiceBoxes(chInitialVA_OS, chPostopVA_OS, ch2WeekVA_OS, ch6WeekVA_OS, chFinalVA_OS);

        chStatus.setItems(FXCollections.observableArrayList(Choices.status));

        chSurgType_OD.setItems(FXCollections.observableArrayList(Choices.surgeryType));
        chSurgType_OS.setItems(FXCollections.observableArrayList(Choices.surgeryType));
    }

    private void setupVAChoiceBoxes(ChoiceBox<String> chInitialVAOd, ChoiceBox<String> chPostopVAOd, ChoiceBox<String> ch2WeekVAOd, ChoiceBox<String> ch6WeekVAOd, ChoiceBox<String> chFinalVAOd) {
        chInitialVAOd.setItems(FXCollections.observableArrayList(Choices.VAList));
        chPostopVAOd.setItems(FXCollections.observableArrayList(Choices.VAList));
        ch2WeekVAOd.setItems(FXCollections.observableArrayList(Choices.VAList));
        ch6WeekVAOd.setItems(FXCollections.observableArrayList(Choices.VAList));
        chFinalVAOd.setItems(FXCollections.observableArrayList(Choices.VAList));
    }

    @javafx.fxml.FXML
    public void goToSearch(ActionEvent actionEvent) throws IOException {
        stage = (Stage) root.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/carrot/catatrack/search-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    @javafx.fxml.FXML
    public void AddPatient(ActionEvent actionEvent) {

    }
}