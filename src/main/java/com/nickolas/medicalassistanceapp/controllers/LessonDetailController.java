package com.nickolas.medicalassistanceapp.controllers;

import com.nickolas.medicalassistanceapp.model.Lesson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.List;

public class LessonDetailController {
    @FXML
    private Label title;

    @FXML
    private Label content;

    @FXML
    private Button prevBtn;

    @FXML
    private Button nextBtn;

    private MainController mainController;

    private List<Lesson> lessons;
    private int currentIndex;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setLessonsData(List<Lesson> lessons, int index) {
        this.lessons = lessons;
        this.currentIndex = index;
        showLesson();
    }

    private void showLesson() {
        Lesson lesson = lessons.get(currentIndex);

        title.setText(lesson.getTitle());
        content.setText(lesson.getContent());

        prevBtn.setVisible(currentIndex > 0);
        nextBtn.setVisible(currentIndex < lessons.size() - 1);
    }

    @FXML
    private void nextLesson() {
        if (currentIndex < lessons.size() - 1) {
            currentIndex++;
            showLesson();
        }
    }

    @FXML
    private void prevLesson() {
        if (currentIndex > 0) {
            currentIndex--;
            showLesson();
        }
    }

    @FXML
    private void goBack() {
        mainController.showLessons();
    }
}
