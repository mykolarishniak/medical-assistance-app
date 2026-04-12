package com.nickolas.medicalassistanceapp.controllers;

import com.nickolas.medicalassistanceapp.dao.LessonDAO;
import com.nickolas.medicalassistanceapp.model.Lesson;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AdminLessonController {
    @FXML
    private ListView<Lesson> lessonList;

    @FXML
    private TextField titleField;

    @FXML
    private TextArea contentArea;

    private final LessonDAO dao = new LessonDAO();

    private Lesson selectedLesson;

    @FXML
    public void initialize() {
        loadLessons();

        lessonList.setOnMouseClicked(e -> selectLesson());
    }

    private void loadLessons() {
        lessonList.setItems(FXCollections.observableArrayList(dao.getAllLessons()));
    }

    private void selectLesson() {
        selectedLesson = lessonList.getSelectionModel().getSelectedItem();

        if (selectedLesson != null) {
            titleField.setText(selectedLesson.getTitle());
            contentArea.setText(selectedLesson.getContent());
        }
    }

    @FXML
    private void addLesson() {
        Lesson lesson = new Lesson(
                0,
                titleField.getText(),
                contentArea.getText()
        );

        dao.addLesson(lesson);
        clearFields();
        loadLessons();
    }

    @FXML
    private void updateLesson() {
        if (selectedLesson == null) return;

        selectedLesson.setTitle(titleField.getText());
        selectedLesson.setContent(contentArea.getText());

        dao.updateLesson(selectedLesson);
        loadLessons();
    }

    @FXML
    private void deleteLesson() {
        if (selectedLesson == null) return;

        dao.deleteLesson(selectedLesson.getId());
        clearFields();
        loadLessons();
    }

    private void clearFields() {
        titleField.clear();
        contentArea.clear();
        selectedLesson = null;
    }
}
