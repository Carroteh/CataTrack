package com.carrot.catatrack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        URL url = getClass().getResource("/com/carrot/catatrack/catatrack.db");
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("connection to DB successful");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("search-view.fxml"));
        URL stylesheet = getClass().getResource("/com/carrot/catatrack/styles.css");
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(String.valueOf(stylesheet));
        stage.setTitle("CataTrack");
        stage.setScene(scene);
        stage.setMaxWidth(1200);
        stage.show();
    }
}
