package com.carrot.catatrack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;

/**
 * @author Philip Mathee
 * @version 1.0
 * Main entry point of the program
 */
public class Main extends Application {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        logger.info("Program launch.");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/carrot/catatrack/views/searchView.fxml"));
        URL stylesheet = getClass().getResource("/com/carrot/catatrack/styles/styles.css");
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(String.valueOf(stylesheet));
        stage.setTitle("CataTrack");
        stage.setScene(scene);
        stage.setMaxWidth(1220);
        stage.show();
    }
}
