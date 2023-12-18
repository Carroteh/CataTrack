package com.carrot.catatrack.model;

import com.carrot.catatrack.db.DatabaseService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


import java.sql.Date;

/**
 * @author Philip Mathee
 * @version 1.0
 * Class that creates a pane for a patient and handles updating of patient information
 */
public class PatientPane extends TitledPane {

    //GENERAL
    private final Patient patient;
    private final Eye rightEye;
    private final Eye leftEye;
    private Button btnSave;
    private Button btnDelete;
    private TextField txtSurname;
    private TextField txtID;
    private TextField txtInitials;
    private TextField txtContact;
    private TextField txtAltContact;
    private ChoiceBox<String> chStatus;
    private Label lblResponse;

    //RIGHT EYE
    private ChoiceBox<String> chLens_OD;
    private ChoiceBox<String> chInitialVA_OD;
    private ChoiceBox<String> chPostopVA_OD;
    private ChoiceBox<String> ch2WeeksVA_OD;
    private ChoiceBox<String> ch6WeeksVA_OD;
    private TextField txtFinalVA_OD;
    private DatePicker dateSurg_OD;
    private ChoiceBox<String> chSurgType_OD;
    private ChoiceBox<String> chSurgPlace_OD;
    private TextArea txtSurgNotes_OD;

    //LEFT EYE
    private ChoiceBox<String> chLens_OS;
    private ChoiceBox<String> chInitialVA_OS;
    private ChoiceBox<String> chPostopVA_OS;
    private ChoiceBox<String> ch2WeeksVA_OS;
    private ChoiceBox<String> ch6WeeksVA_OS;
    private TextField txtFinalVA_OS;
    private DatePicker dateSurg_OS;
    private ChoiceBox<String> chSurgType_OS;
    private ChoiceBox<String> chSurgPlace_OS;
    private TextArea txtSurgNotes_OS;
    private final int number;


    public PatientPane(Person person, int number) {
        this.number = number;
        this.rightEye = person.getRightEye();
        this.leftEye = person.getLeftEye();
        this.patient = person.getPatient();


        String dob = patient.getId_num().length() != 6 && patient.getId_num().length() != 13  ? "" : patient.getId_num().substring(0, 6);
        this.setText(this.number + " ~ " + patient.getSurname() + " " + patient.getInitials() + " ~ "
                + dob + " ~ " + rightEye.getVa_final() + " | " + leftEye.getVa_final() + " ~ " + patient.getStatus() + " ~ " + patient.getContact() + " ~ " + patient.getAlt_contact());

        //Display content when pane is expanded
        this.expandedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // TitledPane is expanded
                setupControls();
                setControlValues();
                setSaveOnAction();
                setDeleteOnAction();
            }
        });

    }

    /**
     * Function that deletes a patients record and Eye data in the database when the Delete button is clicked.
     */
    private void setDeleteOnAction() {
        btnDelete.setOnAction(e -> {
            DatabaseService db = new DatabaseService();

            boolean success = db.deletePatient(this.patient.getPatient_id());

            if(success) {
                lblResponse.setText("Successfully deleted.");
                btnSave.setOnAction(ex -> {
                    lblResponse.setText("Already deleted!");
                });
            }
            else {
                lblResponse.setText("Failed to delete.");
            }
        });
    }

    /**
     * Function that updates the Patient and Eye data in the database once the Save button is clicked
     */
    private void setSaveOnAction() {
        btnSave.setOnAction(e -> {
            //DatabaseService instance
            DatabaseService db = new DatabaseService();

            //Create new patient
            Patient updatedPatient = new Patient(
                    this.patient.getPatient_id(),
                    txtID.getText(),
                    txtSurname.getText(),
                    txtInitials.getText(),
                    Date.valueOf("1000-01-01"),
                    chStatus.getValue(),
                    txtContact.getText(),
                    txtAltContact.getText()
            );

            Eye updatedRightEye = new Eye(
                    this.patient.getPatient_id(),
                    'R',
                    chLens_OD.getValue(),
                    chInitialVA_OD.getValue(),
                    chPostopVA_OD.getValue(),
                    ch2WeeksVA_OD.getValue(),
                    ch6WeeksVA_OD.getValue(),
                    chSurgPlace_OD.getValue(),
                    Date.valueOf(DateUtils.getDate(dateSurg_OD)),
                    chSurgType_OD.getValue(),
                    txtSurgNotes_OD.getText()
            );

            Eye updatedLeftEye = new Eye(
                    this.patient.getPatient_id(),
                    'L',
                    chLens_OS.getValue(),
                    chInitialVA_OS.getValue(),
                    chPostopVA_OS.getValue(),
                    ch2WeeksVA_OS.getValue(),
                    ch6WeeksVA_OS.getValue(),
                    chSurgPlace_OS.getValue(),
                    Date.valueOf(DateUtils.getDate(dateSurg_OS)),
                    chSurgType_OS.getValue(),
                    txtSurgNotes_OS.getText()
            );

            Person person = new Person(updatedPatient, updatedRightEye, updatedLeftEye);
            boolean success = db.editPatient(person);

            if(success) {
                setResponse("Success!");
            }
            else {
                setResponse(("Something went wrong."));
            }
        });
    }

    private void setResponse(String response) {
        lblResponse.setText(response);
    }

    /**
     * Function that sets the initials values for each control
     */
    private void setControlValues() {
        //General
        txtSurname.setText(patient.getSurname());
        txtID.setText(patient.getId_num());
        txtInitials.setText(patient.getInitials());

        txtContact.setText(patient.getContact());
        txtAltContact.setText(patient.getAlt_contact());
        chStatus.setValue(patient.getStatus());

        //OD
        chLens_OD.setValue(rightEye.getLens());
        chInitialVA_OD.setValue(rightEye.getVa_init());
        chPostopVA_OD.setValue(rightEye.getVa_postop());
        ch2WeeksVA_OD.setValue(rightEye.getVa_2weeks());
        ch6WeeksVA_OD.setValue(rightEye.getVa_6weeks());
        txtFinalVA_OD.setText(rightEye.getVa_final());

        if(!DateUtils.isDefault(rightEye.getSurg_date())) {
            dateSurg_OD.setValue(rightEye.getSurg_date().toLocalDate());
        }
        else {
            dateSurg_OD.setValue(null);
        }


        chSurgType_OD.setValue(rightEye.getSurg_type());
        chSurgPlace_OD.setValue(rightEye.getSurg_place());
        txtSurgNotes_OD.setText(rightEye.getSurg_notes());

        //OS
        chLens_OS.setValue(leftEye.getLens());
        chInitialVA_OS.setValue(leftEye.getVa_init());
        chPostopVA_OS.setValue(leftEye.getVa_postop());
        ch2WeeksVA_OS.setValue(leftEye.getVa_2weeks());
        ch6WeeksVA_OS.setValue(leftEye.getVa_6weeks());
        txtFinalVA_OS.setText(leftEye.getVa_final());

        if(!DateUtils.isDefault(leftEye.getSurg_date())) {
            dateSurg_OS.setValue(leftEye.getSurg_date().toLocalDate());
        }
        else {
            dateSurg_OS.setValue(null);
        }

        chSurgType_OS.setValue(leftEye.getSurg_type());
        chSurgPlace_OS.setValue(leftEye.getSurg_place());
        txtSurgNotes_OS.setText(leftEye.getSurg_notes());


    }

    /**
     * Function that creates and places the controls
     */
    private void setupControls() {
        VBox vBox = new VBox();
        vBox.setId("patientVBox");

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
        txtSurname = new TextField();
        txtInitials = new TextField();
        txtID = new TextField();
        txtContact = new TextField();
        txtAltContact = new TextField();
        chStatus = new ChoiceBox<>(FXCollections.observableArrayList(Choices.status));
        lblResponse = new Label("");
        lblResponse.setId("Response");
        btnSave = new Button("Save");
        btnSave.setId("SaveButton");
        btnDelete = new Button("Delete");
        btnDelete.setId("DeleteButton");

        //Setup general info
        genGrid.add(new HBox(new Label("Surname:") , txtSurname),0,0);
        genGrid.add(new HBox(new Label("Contact 1:") , txtContact),0,1);
        genGrid.add(new HBox(new Label("ID:") , txtID),0,2);
        genGrid.add(new HBox(new Label("Initials:"), txtInitials),1,0);
        genGrid.add(new HBox(new Label("Contact 2:") , txtAltContact),1,1);
        genGrid.add(new HBox(new Label("Status:") , chStatus),1,2);
        genGrid.add(new HBox(new Label(""), btnSave),2,0);
        genGrid.add(new HBox(new Label("Response:"),lblResponse), 2, 1);
        genGrid.add(new HBox(new Label(""), btnDelete), 2,2);


        vBox.getChildren().add(genGrid);

        //OD and OS controls
        chInitialVA_OD = new ChoiceBox<>(FXCollections.observableArrayList(Choices.VAList));
        chPostopVA_OD = new ChoiceBox<>(FXCollections.observableArrayList(Choices.VAList));
        ch2WeeksVA_OD = new ChoiceBox<>(FXCollections.observableArrayList(Choices.VAList));
        ch6WeeksVA_OD = new ChoiceBox<>(FXCollections.observableArrayList(Choices.VAList));
        txtFinalVA_OD = new TextField();
        txtFinalVA_OD.setEditable(false);

        chInitialVA_OS = new ChoiceBox<>(FXCollections.observableArrayList(Choices.VAList));
        chPostopVA_OS = new ChoiceBox<>(FXCollections.observableArrayList(Choices.VAList));
        ch2WeeksVA_OS = new ChoiceBox<>(FXCollections.observableArrayList(Choices.VAList));
        ch6WeeksVA_OS = new ChoiceBox<>(FXCollections.observableArrayList(Choices.VAList));
        txtFinalVA_OS = new TextField();
        txtFinalVA_OS.setEditable(false);

        dateSurg_OD = new DatePicker();
        dateSurg_OS = new DatePicker();
        chSurgType_OD = new ChoiceBox<>(FXCollections.observableArrayList(Choices.surgeryType));
        chSurgType_OS = new ChoiceBox<>(FXCollections.observableArrayList(Choices.surgeryType));
        chSurgPlace_OD = new ChoiceBox<>(FXCollections.observableArrayList(Choices.surgPlaces));
        chSurgPlace_OS = new ChoiceBox<>(FXCollections.observableArrayList(Choices.surgPlaces));
        txtSurgNotes_OD = new TextArea();
        txtSurgNotes_OS = new TextArea();
        chLens_OD = new ChoiceBox<>(FXCollections.observableArrayList(Choices.Lens));
        chLens_OS = new ChoiceBox<>(FXCollections.observableArrayList(Choices.Lens));
        txtSurgNotes_OD.setId("SurgNotes");
        txtSurgNotes_OS.setId("SurgNotes");

        //OS and OD grids
        ODGrid.add(new HBox(new Label("Lens:"),chLens_OD),0,0);
        ODGrid.add(new Label("VA"), 0,1);
        ODGrid.add(new HBox(new Label("Initial:"), chInitialVA_OD),0,2);
        ODGrid.add(new HBox(new Label("Postop:") , chPostopVA_OD),0,3);
        ODGrid.add(new HBox(new Label("2 Weeks:") , ch2WeeksVA_OD),0,4);
        ODGrid.add(new HBox(new Label("6 Weeks:") , ch6WeeksVA_OD),0,5);
        ODGrid.add(new HBox(new Label("Final:") , txtFinalVA_OD),0,6);
        ODGrid.add(new Label("Surgery"),1,1);
        ODGrid.add(new HBox(new Label("Date") , dateSurg_OD),1,2);
        ODGrid.add(new HBox(new Label("Type:") , chSurgType_OD),1,3);
        ODGrid.add(new HBox(new Label("Place:") , chSurgPlace_OD),1,4);
        ODGrid.add(new HBox(new Label("Notes:") , txtSurgNotes_OD),1,5, 1,2);

        OSGrid.add(new HBox(new Label("Lens:"),chLens_OS),0,0);
        OSGrid.add(new Label("VA"), 0,1);
        OSGrid.add(new HBox(new Label("Initial:"), chInitialVA_OS),0,2);
        OSGrid.add(new HBox(new Label("Postop:") , chPostopVA_OS),0,3);
        OSGrid.add(new HBox(new Label("2 Weeks:") , ch2WeeksVA_OS),0,4);
        OSGrid.add(new HBox(new Label("6 Weeks:") , ch6WeeksVA_OS),0,5);
        OSGrid.add(new HBox(new Label("Final:") , txtFinalVA_OS),0,6);
        OSGrid.add(new Label("Surgery"),1,1);
        OSGrid.add(new HBox(new Label("Date") , dateSurg_OS),1,2);
        OSGrid.add(new HBox(new Label("Type:") , chSurgType_OS),1,3);
        OSGrid.add(new HBox(new Label("Place:") , chSurgPlace_OS),1,4);
        OSGrid.add(new HBox(new Label("Notes:") , txtSurgNotes_OS),1,5, 1,2);

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

        this.setContent(mainAnchor);
    }
}
