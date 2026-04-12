package com.nickolas.medicalassistanceapp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class MainController {
    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        loadPage("hello-view.fxml");
    }

    public void loadPage(String fxml) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/nickolas/medicalassistanceapp/" + fxml)
            );

            Node node = loader.load();

            contentArea.getChildren().setAll(node);

            Object controller = loader.getController();

            if (controller instanceof LessonController lessonController) {
                lessonController.setMainController(this);
            }

            if (controller instanceof TestListController testListController) {
                testListController.setMainController(this);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setContent(Node node) {
        contentArea.getChildren().setAll(node);
    }

    @FXML
    private void showHome() {
        loadPage("hello-view.fxml");
    }

    @FXML
      void showLessons() {
        loadPage("lesson-view.fxml");
    }

    @FXML
    private void showTests() {
        loadPage("test-list-view.fxml");
    }

    @FXML
    private void showAdvice() {
        loadPage("hello-view.fxml");
    }

    @FXML
    private void showProgress() {
        loadPage("hello-view.fxml");
    }

    @FXML
    private void showAdmin() {
        loadPage("admin-view.fxml");
    }

}
