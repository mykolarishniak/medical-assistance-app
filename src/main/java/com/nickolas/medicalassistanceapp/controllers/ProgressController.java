package com.nickolas.medicalassistanceapp.controllers;

import com.nickolas.medicalassistanceapp.dao.AnswerDAO;
import com.nickolas.medicalassistanceapp.dao.ProgressDAO;
import com.nickolas.medicalassistanceapp.model.AnswerDetail;
import com.nickolas.medicalassistanceapp.model.ProgressDetail;
import com.nickolas.medicalassistanceapp.service.ProgressService;
import com.nickolas.medicalassistanceapp.session.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class ProgressController {
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label percentLabel;
    @FXML
    private Label lessonsLabel;
    @FXML
    private Label testsLabel;
    @FXML
    private ListView<String> detailsList;
    @FXML private ListView<String> answersList;
    @FXML
    private Button resetButton;


    private ProgressService service = new ProgressService();
    private ProgressDAO dao = new ProgressDAO();
    private AnswerDAO answerDAO = new AnswerDAO();

    @FXML
    public void initialize() {
        refresh();
    }

    public void refresh() {
        boolean loggedIn = Session.isLoggedIn();
        resetButton.setVisible(loggedIn);
        resetButton.setManaged(loggedIn);
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
        percentLabel.setText((int) (progress * 100) + "%");

        lessonsLabel.setText("Уроків пройдено: " +
                dao.getCompletedLessons(userId) + "/" +
                dao.getTotalLessons());

        testsLabel.setText("Тестів пройдено: " +
                dao.getCompletedTests(userId));
        loadDetails(userId);
        loadAnswers(userId);
    }

    private void loadDetails(int userId) {
        detailsList.getItems().clear();

        for (ProgressDetail d : dao.getProgressDetails(userId)) {

            String status = d.isLessonCompleted() ? "✅" : "❌";

            String testInfo = d.getTotal() > 0
                    ? " | Тест: " + d.getCorrect() + "/" + d.getTotal()
                    : " | Тест: -";

            detailsList.getItems().add(
                    status + " " + d.getLessonTitle() + testInfo
            );
        }
    }
    @FXML
    private void handleReset() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Скидання прогресу");
        alert.setHeaderText("Ви впевнені?");
        alert.setContentText("Весь прогрес буде видалено!");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            dao.resetProgress(Session.getCurrentUser().getId());
            refresh();
        }
    }
    private void loadAnswers(int userId) {
        answersList.getItems().clear();

        for (AnswerDetail a : answerDAO.getUserAnswers(userId)) {

            String icon = a.isCorrect() ? "✅" : "❌";

            answersList.getItems().add(
                    icon + " " + a.getQuestionText() +
                            "\nВаша відповідь: " + a.getSelected() +
                            "\nПравильна відповідь: " + a.getCorrect()
            );
        }
    }

}
