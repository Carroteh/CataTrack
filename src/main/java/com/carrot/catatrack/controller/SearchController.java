package com.carrot.catatrack.controller;

import com.carrot.catatrack.model.Choices;
import com.carrot.catatrack.model.Patient;
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

    private void addPatientToAccordion(Patient patient) {
        VBox vBox = new VBox();
        vBox.setId("patientVBox");

        TitledPane mainPane = new TitledPane();
        TitledPane OSPane = new TitledPane();
        TitledPane ODPane = new TitledPane();

        GridPane genGrid = new GridPane();
        genGrid.setId("GridPane");
        GridPane ODGrid = new GridPane();
        ODGrid.setId("EyeGrid");
        GridPane OSGrid = new GridPane();
        OSGrid.setId("EyeGrid");

        AnchorPane mainAnchor = new AnchorPane();
        AnchorPane ODAnchor = new AnchorPane();
        AnchorPane OSAnchor = new AnchorPane();

        Accordion eyeAccordion = new Accordion();

        //General info controls
        TextField txtSurname = new TextField();
        TextField txtInitials = new TextField();
        TextField txtID = new TextField();
        DatePicker dateBirth = new DatePicker();
        ChoiceBox chStatus = new ChoiceBox(FXCollections.observableArrayList(Choices.status));
        Label lblResponse = new Label("This is a response fr fr fr ");
        lblResponse.setId("Response");
        Button btnSave = new Button("Save");
        btnSave.setId("SaveButton");

        //Setup general info
        genGrid.add(new HBox(new Label("Surname:") , txtSurname),0,0);
        genGrid.add(new HBox(new Label("Initials:"), txtInitials),0,1);
        genGrid.add(new HBox(new Label("ID:") , txtID),1,0);
        genGrid.add(new HBox(new Label("Birthdate:") , dateBirth),1,1);
        genGrid.add(new HBox(new Label("Status:") , chStatus),2,0);
        genGrid.add(new Label("Response:"), 3, 0);
        genGrid.add(lblResponse, 3, 1);
        genGrid.add(btnSave,2,1);

        vBox.getChildren().add(genGrid);

        //OD and OS controls
        ChoiceBox chInitialVA_OD = new ChoiceBox(FXCollections.observableArrayList(Choices.VAList));
        ChoiceBox chPostOpVA_OD = new ChoiceBox(FXCollections.observableArrayList(Choices.VAList));
        ChoiceBox ch2WeeksVA_OD = new ChoiceBox(FXCollections.observableArrayList(Choices.VAList));
        ChoiceBox ch6WeeksVA_OD = new ChoiceBox(FXCollections.observableArrayList(Choices.VAList));
        ChoiceBox chFinalVA_OD = new ChoiceBox(FXCollections.observableArrayList(Choices.VAList));

        ChoiceBox chInitialVA_OS = new ChoiceBox(FXCollections.observableArrayList(Choices.VAList));
        ChoiceBox chPostOpVA_OS = new ChoiceBox(FXCollections.observableArrayList(Choices.VAList));
        ChoiceBox ch2WeeksVA_OS = new ChoiceBox(FXCollections.observableArrayList(Choices.VAList));
        ChoiceBox ch6WeeksVA_OS = new ChoiceBox(FXCollections.observableArrayList(Choices.VAList));
        ChoiceBox chFinalVA_OS = new ChoiceBox(FXCollections.observableArrayList(Choices.VAList));

        DatePicker dateSurg_OD = new DatePicker();
        DatePicker dateSurg_OS = new DatePicker();
        ChoiceBox chTypeSurg_OD = new ChoiceBox(FXCollections.observableArrayList(Choices.surgeryType));
        ChoiceBox chTypeSurg_OS = new ChoiceBox(FXCollections.observableArrayList(Choices.surgeryType));
        TextField txtPlace_OD = new TextField();
        TextField txtPlace_OS = new TextField();
        TextArea txtNotes_OD = new TextArea();
        txtNotes_OD.setId("SurgNotes");
        TextArea txtNotes_OS = new TextArea();
        txtNotes_OS.setId("SurgNotes");

        //OS and OD grids
        ODGrid.add(new Label("VA"), 0,0);
        ODGrid.add(new HBox(new Label("Initial:"), chInitialVA_OD),0,1);
        ODGrid.add(new HBox(new Label("Postop:") , chPostOpVA_OD),0,2);
        ODGrid.add(new HBox(new Label("2 Weeks:") , ch2WeeksVA_OD),0,3);
        ODGrid.add(new HBox(new Label("6 Weeks:") , ch6WeeksVA_OD),0,4);
        ODGrid.add(new HBox(new Label("Final:") , chFinalVA_OD),0,5);
        ODGrid.add(new Label("Surgery"),1,0);
        ODGrid.add(new HBox(new Label("Date") , dateSurg_OD),1,1);
        ODGrid.add(new HBox(new Label("Type:") , chTypeSurg_OD),1,2);
        ODGrid.add(new HBox(new Label("Place:") , txtPlace_OD),1,3);
        ODGrid.add(new HBox(new Label("Notes:") , txtNotes_OD),1,4, 1,2);

        OSGrid.add(new Label("VA"), 0,0);
        OSGrid.add(new HBox(new Label("Initial:"), chInitialVA_OS),0,1);
        OSGrid.add(new HBox(new Label("Postop:") , chPostOpVA_OS),0,2);
        OSGrid.add(new HBox(new Label("2 Weeks:") , ch2WeeksVA_OS),0,3);
        OSGrid.add(new HBox(new Label("6 Weeks:") , ch6WeeksVA_OS),0,4);
        OSGrid.add(new HBox(new Label("Final:") , chFinalVA_OS),0,5);
        OSGrid.add(new Label("Surgery"),1,0);
        OSGrid.add(new HBox(new Label("Date") , dateSurg_OS),1,1);
        OSGrid.add(new HBox(new Label("Type:") , chTypeSurg_OS),1,2);
        OSGrid.add(new HBox(new Label("Place:") , txtPlace_OS),1,3);
        OSGrid.add(new HBox(new Label("Notes:") , txtNotes_OS),1,4, 1,2);

        ODAnchor.getChildren().add(ODGrid);
        OSAnchor.getChildren().add(OSGrid);

        OSPane.setText("OS");
        ODPane.setText("OD");
        ODPane.setContent(ODAnchor);
        OSPane.setContent(OSAnchor);

        eyeAccordion.getPanes().add(ODPane);
        eyeAccordion.getPanes().add(OSPane);

        vBox.getChildren().add(eyeAccordion);

        mainAnchor.getChildren().add(vBox);

        mainPane.setContent(mainAnchor);

        mainPane.setText("Surname, IN, 1234567890");

        patientAccordion.getPanes().add(mainPane);
    }

    @javafx.fxml.FXML
    public void initialize() {
        lblSurgDate.setId("Response");
        lblDOB.setId("Response");
        for (int i=0; i<5; i++) {
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