package com.nickolas.medicalassistanceapp.controllers;

import com.nickolas.medicalassistanceapp.session.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class MainController {
    @FXML
    private StackPane contentArea;

    @FXML private Label userLabel;
    @FXML private Button loginBtn;
    @FXML private Button registerBtn;
    @FXML private Button logoutBtn;


    @FXML
    public void initialize() {
        loadPage("hello-view.fxml");
        updateUserUI();
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

            if (controller instanceof LoginController loginController) {
                loginController.setMainController(this);
            }

            if (controller instanceof RegisterController registerController) {
                registerController.setMainController(this);
            }
            if (controller instanceof TestController testController) {
                testController.setMainController(this);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setContent(Node node) {
        contentArea.getChildren().setAll(node);
    }

     void updateUserUI() {

        if (Session.isLoggedIn()) {

            userLabel.setText("👤 " + Session.getCurrentUser().getUsername());

            loginBtn.setVisible(false);
            registerBtn.setVisible(false);
            logoutBtn.setVisible(true);

        } else {

            userLabel.setText("Гість");

            loginBtn.setVisible(true);
            registerBtn.setVisible(true);
            logoutBtn.setVisible(false);
        }
    }

    @FXML
     void showHome() {
        loadPage("hello-view.fxml");
    }

    @FXML
      void showLessons() {
        loadPage("lesson-view.fxml");
    }

    @FXML
    void showTests() {
        loadPage("test-list-view.fxml");
    }

    @FXML
    private void showAdvice() {
        loadPage("advice-view.fxml");
    }

    @FXML
    private void showProgress() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/nickolas/medicalassistanceapp/progress-view.fxml")
            );

            Node node = loader.load();

            ProgressController controller = loader.getController();

            controller.refresh();

            contentArea.getChildren().setAll(node);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showAdmin() {
        if (!Session.isLoggedIn()) {
            showAlert(
                    "Помилка доступу",
                    "Спочатку увійдіть у систему!"
            );
            return;
        }

        if (!Session.isAdmin()) {
            showAlert(
                    "Доступ заборонено",
                    "У вас немає прав адміністратора!"
            );
            return;
        }

        loadPage("admin-view.fxml");
    }


    @FXML
    public void openLogin() {
        loadPage("login-view.fxml");
    }

    @FXML
    public void openRegister() {
        loadPage("register-view.fxml");
    }

    @FXML
    private void logout() {

        Session.logout();

        updateUserUI();

        loadPage("hello-view.fxml");
    }


    private void showAlert(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

}
