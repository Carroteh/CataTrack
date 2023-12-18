package com.carrot.catatrack.controller;

import com.carrot.catatrack.db.DatabaseService;
import com.carrot.catatrack.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private CheckBox chkMonth;
    @FXML
    private DatePicker dateSurgFilter;
    @FXML
    private ChoiceBox<String> chSurgTypeFilter;
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

    private static final Logger logger = LogManager.getLogger(SearchController.class);
    @FXML
    private MenuItem itmAddPatient;
    @FXML
    private Button btnPatientSearch;
    @FXML
    private Button btnGeneralSearch;
    private int currentIndex;
    private boolean completed;
    ArrayList<Person> results;
    @FXML
    private Button btnLoadMorePatientSearch;
    @FXML
    private Button btnLoadMoreGeneralSearch;

    private void addPersonToAccordion(Person person, int count) {
        PatientPane pane = new PatientPane(person, count);
        patientAccordion.getPanes().add(pane);
    }

    @javafx.fxml.FXML
    public void initialize() {
        logger.info("Initialize search controller.");
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
        btnLoadMorePatientSearch.setVisible(false);
        btnLoadMoreGeneralSearch.setVisible(false);
    }

    @javafx.fxml.FXML
    public void goToAddPatient() throws IOException {
        //Change view to addPatientView.fxml
        Stage stage = (Stage) root.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/carrot/catatrack/views/addPatientView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    @FXML
    public void doPatientSearch() {
        completed = false;
        btnLoadMoreGeneralSearch.setVisible(false);
        btnLoadMorePatientSearch.setVisible(true);

        currentIndex = 0;

        logger.info("Initiating Patient search: {} ~ {} ~ {}", txtSurname.getText(), txtInitials.getText(), txtID.getText());

        clearResults();

        results = db.patientSearch(txtSurname.getText(), txtInitials.getText(),Date.valueOf("1000-01-01"), txtID.getText(), "N/A", "", "");

        loadResults();
    }

    @FXML
    public void doGeneralSearch() {
        completed = false;
        btnLoadMoreGeneralSearch.setVisible(true);
        btnLoadMorePatientSearch.setVisible(false);

        currentIndex = 0;

        logger.info("Initiating General search: {} ~ {} ~ {} ~ {} ~ {} ~ {} ~ {} ~ {}", DateUtils.getDate(dateSurgFilter).toString(), String.valueOf(chkMonth.isSelected()), chLensFilter.getValue(), chStatusFilter.getValue(), chVAFilter1.getValue(), chVAFilter2.getValue(), chSurgTypeFilter.getValue(), chPlaceFilter.getValue());

        clearResults();

        results = db.generalSearch(Date.valueOf(DateUtils.getDate(dateSurgFilter)), chkMonth.isSelected(), chLensFilter.getValue(), chStatusFilter.getValue() ,chVAFilter1.getValue(), chVAFilter2.getValue(), chSurgTypeFilter.getValue(), chPlaceFilter.getValue());

        loadResults();
    }

    /**
     * Function that loads 100 results
     */
    private void loadResults() {
        if(results != null && !completed) {
            for(int i=currentIndex; i<currentIndex+100; i++) {
                if(i+currentIndex == results.size()) {
                    completed = true;
                    break;
                }
                addPersonToAccordion(results.get(i), i+1);
            }
            currentIndex += 100;
            lblNoResults.setText(results.size() + " Results");
            logger.info("{} General search results.", results.size());
        }
    }

    private void clearResults() {
        patientAccordion.getPanes().clear();
    }

    @FXML
    public void loadMoreResults() {
        loadResults();
    }
}