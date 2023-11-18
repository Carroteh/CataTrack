package com.carrot.catatrack.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController
{
    Stage stage;
    @javafx.fxml.FXML
    private Button btnSearch;
    @javafx.fxml.FXML
    private Button btnPatient;
    @javafx.fxml.FXML
    private AnchorPane root;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void goToSearch(ActionEvent actionEvent) throws IOException {
        stage = (Stage) root.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/carrot/catatrack/search-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    @javafx.fxml.FXML
    public void goToPatient(ActionEvent actionEvent) throws IOException {
        stage = (Stage) root.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/carrot/catatrack/addPatient-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }
}