package com.nickolas.medicalassistanceapp.controllers;

import com.nickolas.medicalassistanceapp.dao.AnswerDAO;
import com.nickolas.medicalassistanceapp.dao.ProgressDAO;
import com.nickolas.medicalassistanceapp.dao.QuestionDAO;
import com.nickolas.medicalassistanceapp.model.Answer;
import com.nickolas.medicalassistanceapp.model.Lesson;
import com.nickolas.medicalassistanceapp.model.Question;
import com.nickolas.medicalassistanceapp.session.Session;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class TestController {
    @FXML
    private Label questionLabel;

    @FXML
    private VBox answersBox;

    @FXML
    private Label resultLabel;

    private List<Question> questions;
    private int currentIndex = 0;
    private int score = 0;

    private final QuestionDAO questionDAO = new QuestionDAO();
    private final AnswerDAO answerDAO = new AnswerDAO();

    private Lesson lesson;

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
        questions = questionDAO.getByLesson(lesson.getId());
        showQuestion();
    }

    private void showQuestion() {

        if (questions == null || questions.isEmpty()) {
            questionLabel.setText("Немає тестів для цього уроку");
            answersBox.getChildren().clear();
            resultLabel.setText("");
            return;
        }

        if (currentIndex >= questions.size()) {
            questionLabel.setText("Тест завершено!");
            answersBox.getChildren().clear();
            resultLabel.setText("Ваш результат: " + score + "/" + questions.size());
            if (Session.isLoggedIn()) {
                ProgressDAO dao = new ProgressDAO();

                dao.saveTestResult(
                        Session.getCurrentUser().getId(),
                        lesson.getId(),
                        score,
                        questions.size()
                );
            }
            return;
        }

        Question q = questions.get(currentIndex);

        questionLabel.setText(q.getQuestion());
        answersBox.getChildren().clear();
        resultLabel.setText("");

        List<Answer> answers = answerDAO.getByQuestion(q.getId());

        for (Answer a : answers) {
            Button btn = new Button(a.getAnswer());

            btn.setMaxWidth(Double.MAX_VALUE);
            btn.getStyleClass().add("answer-btn");

            btn.setOnAction(e -> checkAnswer(a));

            answersBox.getChildren().add(btn);
        }
    }

    private void checkAnswer(Answer answer) {

        if (answer.isCorrect()) {
            resultLabel.setText("✔ Правильно");
            score++;
        } else {
            resultLabel.setText("❌ Неправильно");
        }

        currentIndex++;

        new Thread(() -> {
            try {
                Thread.sleep(800);
                Platform.runLater(this::showQuestion);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
