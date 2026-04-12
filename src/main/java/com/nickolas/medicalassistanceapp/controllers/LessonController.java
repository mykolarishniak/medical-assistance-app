package com.nickolas.medicalassistanceapp.controllers;

import com.nickolas.medicalassistanceapp.dao.LessonDAO;
import com.nickolas.medicalassistanceapp.model.Lesson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class LessonController {
    @FXML
    private VBox lessonContainer;

    private MainController mainController;
    private final LessonDAO dao = new LessonDAO();

    private List<Lesson> lessons;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        lessonContainer.getChildren().clear();
        lessons = dao.getAllLessons();

        for (int i = 0; i < lessons.size(); i++) {
            addLessonCard(lessons.get(i), i);
        }
    }

    private void addLessonCard(Lesson lesson, int index) {

        VBox card = new VBox();
        card.getStyleClass().add("lesson-card");

        Label title = new Label(lesson.getTitle());
        title.getStyleClass().add("lesson-title");

        Button open = new Button("Читати");
        open.getStyleClass().add("open-btn");

        Button testBtn = new Button("Пройти тест");
        testBtn.getStyleClass().add("test-btn");

        open.setOnAction(e -> openLesson(index));
        testBtn.setOnAction(e -> openTest(lesson));

        card.getChildren().addAll(title, open, testBtn);

        lessonContainer.getChildren().add(card);
    }

    private void openLesson(int index) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/nickolas/medicalassistanceapp/lesson-detail-view.fxml")
            );

            Node node = loader.load();

            LessonDetailController controller = loader.getController();
            controller.setMainController(mainController);
            controller.setLessonsData(lessons, index);

            mainController.setContent(node);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openTest(Lesson lesson) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/nickolas/medicalassistanceapp/test-view.fxml")
            );

            Node node = loader.load();

            TestController controller = loader.getController();
            controller.setLesson(lesson);

            mainController.setContent(node);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
