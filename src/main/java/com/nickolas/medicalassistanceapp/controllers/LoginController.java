package com.nickolas.medicalassistanceapp.controllers;

import com.nickolas.medicalassistanceapp.dao.UserDAO;
import com.nickolas.medicalassistanceapp.model.User;
import com.nickolas.medicalassistanceapp.session.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    private final UserDAO dao = new UserDAO();
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void login() {

        User user = dao.login(
                usernameField.getText(),
                passwordField.getText()
        );

        if (user == null) {
            errorLabel.setText("Невірний логін або пароль");
            return;
        }

        Session.login(user);
        if (mainController != null) {
            mainController.showHome();
            mainController.updateUserUI();
        }
    }
}
