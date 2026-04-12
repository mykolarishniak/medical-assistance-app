module com.nickolas.medicalassistanceapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.nickolas.medicalassistanceapp to javafx.fxml;
    exports com.nickolas.medicalassistanceapp;
    exports com.nickolas.medicalassistanceapp.controllers;
    opens com.nickolas.medicalassistanceapp.controllers to javafx.fxml;
}