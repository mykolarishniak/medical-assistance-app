package com.nickolas.medicalassistanceapp.controllers;

import com.nickolas.medicalassistanceapp.dao.AnswerDAO;
import com.nickolas.medicalassistanceapp.dao.QuestionDAO;
import com.nickolas.medicalassistanceapp.model.Answer;
import com.nickolas.medicalassistanceapp.model.Question;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.util.ArrayList;
import java.util.List;

public class AdminTestController {
    @FXML private ListView<Question> questionList;
    @FXML private TextField questionField;
    @FXML private TextField lessonIdField;
    @FXML private TextField answersField;
    @FXML private TextField correctIndexField;

    private final QuestionDAO questionDAO = new QuestionDAO();
    private final AnswerDAO answerDAO = new AnswerDAO();

    private Question selected;

    @FXML
    public void initialize() {
        loadQuestions();

        questionList.setOnMouseClicked(e -> {
            selected = questionList.getSelectionModel().getSelectedItem();

            if (selected != null) {
                loadAnswers(selected);
            }
        });
    }

    private void loadQuestions() {
        questionList.setItems(FXCollections.observableArrayList(
                questionDAO.getAllQuestions()
        ));
    }

    private void loadAnswers(Question q) {

        questionField.setText(q.getQuestion());
        lessonIdField.setText(String.valueOf(q.getLessonId()));

        List<Answer> answers = answerDAO.getByQuestion(q.getId());

        StringBuilder text = new StringBuilder();
        int correctIndex = 1;

        for (int i = 0; i < answers.size(); i++) {

            text.append(answers.get(i).getAnswer());

            if (i < answers.size() - 1) {
                text.append(";");
            }

            if (answers.get(i).isCorrect()) {
                correctIndex = i + 1;
            }
        }

        answersField.setText(text.toString());
        correctIndexField.setText(String.valueOf(correctIndex));
    }

    @FXML
    private void addQuestion() {

        Question q = new Question(
                0,
                Integer.parseInt(lessonIdField.getText()),
                questionField.getText()
        );

        questionDAO.addQuestion(q);

        insertAnswers(q.getId());

        loadQuestions();
    }

    @FXML
    private void updateQuestion() {

        if (selected == null) return;

        selected.setQuestion(questionField.getText());
        selected.setLessonId(Integer.parseInt(lessonIdField.getText()));
        questionDAO.updateQuestion(selected);

        answerDAO.deleteByQuestion(selected.getId());

        insertAnswers(selected.getId());

        loadQuestions();
    }

    private void insertAnswers(int questionId) {

        String[] answers = answersField.getText().split(";");

        int correct;

        try {
            correct = Integer.parseInt(correctIndexField.getText()) - 1;
        } catch (Exception e) {
            System.out.println("Invalid correct index");
            return;
        }

        if (correct < 0 || correct >= answers.length) {
            System.out.println("Correct index out of range");
            return;
        }

        for (int i = 0; i < answers.length; i++) {

            answerDAO.addAnswer(new Answer(
                    0,
                    questionId,
                    answers[i].trim(),
                    i == correct
            ));
        }
    }

    @FXML
    private void deleteQuestion() {

        if (selected == null) return;

        answerDAO.deleteByQuestion(selected.getId());
        questionDAO.deleteQuestion(selected.getId());

        loadQuestions();
    }
}
