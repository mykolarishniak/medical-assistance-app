package com.nickolas.medicalassistanceapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("main-layout.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        scene.getStylesheets().add(
                getClass().getResource("/com/nickolas/medicalassistanceapp/styles.css").toExternalForm()
        );
        stage.setTitle("Програма для навчання першої допомоги");
        stage.setScene(scene);
        stage.show();
    }
}
