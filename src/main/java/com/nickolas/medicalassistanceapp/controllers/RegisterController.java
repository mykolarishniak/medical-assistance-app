package com.nickolas.medicalassistanceapp.controllers;

import com.nickolas.medicalassistanceapp.dao.UserDAO;
import com.nickolas.medicalassistanceapp.model.User;
import com.nickolas.medicalassistanceapp.session.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private final UserDAO dao = new UserDAO();
    private MainController mainController;

    public void setMainController(MainController mc) {
        this.mainController = mc;
    }

    @FXML
    private void register() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Заповніть всі поля");
            return;
        }

        if (dao.exists(username)) {
            errorLabel.setText("Користувач вже існує");
            return;
        }

        User user = new User(0, username, password, "USER");

        dao.register(user);

        Session.login(user);

        mainController.updateUserUI();

        mainController.showHome();
    }

    @FXML
    private void goLogin() {
        mainController.openLogin();
    }
}
