package com.carrot.catatrack.model;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PatientPane extends TitledPane {

    public PatientPane(Patient patient) {
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
        TextField txtSurname = new TextField();
        TextField txtInitials = new TextField();
        TextField txtID = new TextField();
        TextField txtContact = new TextField();
        TextField txtAltContact = new TextField();
        DatePicker dateBirth = new DatePicker();
        ChoiceBox<String> chStatus = new ChoiceBox<>(FXCollections.observableArrayList(Choices.status));
        Label lblResponse = new Label("");
        lblResponse.setId("Response");
        Button btnSave = new Button("Save");
        btnSave.setId("SaveButton");

        //Setup general info
        genGrid.add(new HBox(new Label("Surname:") , txtSurname),0,0);
        genGrid.add(new HBox(new Label("Initials:"), txtInitials),0,1);
        genGrid.add(new HBox(new Label("Contact 1:") , txtContact),0,2);
        genGrid.add(new HBox(new Label("ID:") , txtID),1,0);
        genGrid.add(new HBox(new Label("Birthdate:") , dateBirth),1,1);
        genGrid.add(new HBox(new Label("Contact 2:") , txtAltContact),1,2);
        genGrid.add(new HBox(new Label("Status:") , chStatus),2,0);
        genGrid.add(btnSave,2,1);
        genGrid.add(new HBox(new Label("Response:"),lblResponse), 2, 2);


        vBox.getChildren().add(genGrid);

        //OD and OS controls
        ChoiceBox<String> chInitialVA_OD = new ChoiceBox<>(FXCollections.observableArrayList(Choices.VAList));
        ChoiceBox<String> chPostOpVA_OD = new ChoiceBox<>(FXCollections.observableArrayList(Choices.VAList));
        ChoiceBox<String> ch2WeeksVA_OD = new ChoiceBox<>(FXCollections.observableArrayList(Choices.VAList));
        ChoiceBox<String> ch6WeeksVA_OD = new ChoiceBox<>(FXCollections.observableArrayList(Choices.VAList));
        ChoiceBox<String> chFinalVA_OD = new ChoiceBox<>(FXCollections.observableArrayList(Choices.VAList));

        ChoiceBox<String> chInitialVA_OS = new ChoiceBox<>(FXCollections.observableArrayList(Choices.VAList));
        ChoiceBox<String> chPostOpVA_OS = new ChoiceBox<>(FXCollections.observableArrayList(Choices.VAList));
        ChoiceBox<String> ch2WeeksVA_OS = new ChoiceBox<>(FXCollections.observableArrayList(Choices.VAList));
        ChoiceBox<String> ch6WeeksVA_OS = new ChoiceBox<>(FXCollections.observableArrayList(Choices.VAList));
        ChoiceBox<String> chFinalVA_OS = new ChoiceBox<>(FXCollections.observableArrayList(Choices.VAList));

        DatePicker dateSurg_OD = new DatePicker();
        DatePicker dateSurg_OS = new DatePicker();
        ChoiceBox<String> chTypeSurg_OD = new ChoiceBox<>(FXCollections.observableArrayList(Choices.surgeryType));
        ChoiceBox<String> chTypeSurg_OS = new ChoiceBox<>(FXCollections.observableArrayList(Choices.surgeryType));
        TextField txtPlace_OD = new TextField();
        TextField txtPlace_OS = new TextField();
        TextArea txtNotes_OD = new TextArea();
        ChoiceBox<String> chLens_OD = new ChoiceBox<>(FXCollections.observableArrayList(Choices.Lens));
        ChoiceBox<String> chLens_OS = new ChoiceBox<>(FXCollections.observableArrayList(Choices.Lens));
        txtNotes_OD.setId("SurgNotes");
        TextArea txtNotes_OS = new TextArea();
        txtNotes_OS.setId("SurgNotes");

        //OS and OD grids
        ODGrid.add(new HBox(new Label("Lens:"),chLens_OD),0,0);
        ODGrid.add(new Label("VA"), 0,1);
        ODGrid.add(new HBox(new Label("Initial:"), chInitialVA_OD),0,2);
        ODGrid.add(new HBox(new Label("Postop:") , chPostOpVA_OD),0,3);
        ODGrid.add(new HBox(new Label("2 Weeks:") , ch2WeeksVA_OD),0,4);
        ODGrid.add(new HBox(new Label("6 Weeks:") , ch6WeeksVA_OD),0,5);
        ODGrid.add(new HBox(new Label("Final:") , chFinalVA_OD),0,6);
        ODGrid.add(new Label("Surgery"),1,1);
        ODGrid.add(new HBox(new Label("Date") , dateSurg_OD),1,2);
        ODGrid.add(new HBox(new Label("Type:") , chTypeSurg_OD),1,3);
        ODGrid.add(new HBox(new Label("Place:") , txtPlace_OD),1,4);
        ODGrid.add(new HBox(new Label("Notes:") , txtNotes_OD),1,5, 1,2);

        OSGrid.add(new HBox(new Label("Lens:"),chLens_OS),0,0);
        OSGrid.add(new Label("VA"), 0,1);
        OSGrid.add(new HBox(new Label("Initial:"), chInitialVA_OS),0,2);
        OSGrid.add(new HBox(new Label("Postop:") , chPostOpVA_OS),0,3);
        OSGrid.add(new HBox(new Label("2 Weeks:") , ch2WeeksVA_OS),0,4);
        OSGrid.add(new HBox(new Label("6 Weeks:") , ch6WeeksVA_OS),0,5);
        OSGrid.add(new HBox(new Label("Final:") , chFinalVA_OS),0,6);
        OSGrid.add(new Label("Surgery"),1,1);
        OSGrid.add(new HBox(new Label("Date") , dateSurg_OS),1,2);
        OSGrid.add(new HBox(new Label("Type:") , chTypeSurg_OS),1,3);
        OSGrid.add(new HBox(new Label("Place:") , txtPlace_OS),1,4);
        OSGrid.add(new HBox(new Label("Notes:") , txtNotes_OS),1,5, 1,2);

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

        this.setText("Surname, IN, +27 38473787, 6/12 | 6/12, 1980/02/01");
    }
}
