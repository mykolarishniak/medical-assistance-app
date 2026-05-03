package com.nickolas.medicalassistanceapp.controllers;

import com.nickolas.medicalassistanceapp.dao.ProgressDAO;
import com.nickolas.medicalassistanceapp.service.ProgressService;
import com.nickolas.medicalassistanceapp.session.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ProgressController {
    @FXML private ProgressBar progressBar;
    @FXML private Label percentLabel;
    @FXML private Label lessonsLabel;
    @FXML private Label testsLabel;

    private ProgressService service = new ProgressService();
    private ProgressDAO dao = new ProgressDAO();

    @FXML
    public void initialize() {
        refresh();
    }
    public void refresh() {
        if (!Session.isLoggedIn()) {
            percentLabel.setText("Увійдіть в систему");
            lessonsLabel.setText("");
            testsLabel.setText("");
            progressBar.setProgress(0);
            return;
        }

        int userId = Session.getCurrentUser().getId();

        double progress = service.getProgress(userId);

        progressBar.setProgress(progress);
        percentLabel.setText((int)(progress * 100) + "%");

        lessonsLabel.setText("Уроків пройдено: " +
                dao.getCompletedLessons(userId) + "/" +
                dao.getTotalLessons());

        testsLabel.setText("Тестів пройдено: " +
                dao.getCompletedTests(userId));
    }
}
