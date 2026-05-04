package com.nickolas.medicalassistanceapp.controllers;

import com.nickolas.medicalassistanceapp.dao.LessonDAO;
import com.nickolas.medicalassistanceapp.model.Lesson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class TestListController {
    @FXML
    private VBox testsContainer;

    private final LessonDAO dao = new LessonDAO();

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {

        List<Lesson> lessons = dao.getAllLessons();

        for (Lesson lesson : lessons) {
            addTestCard(lesson);
        }
    }

    private void addTestCard(Lesson lesson) {

        VBox card = new VBox();
        card.getStyleClass().add("lesson-card");

        Label title = new Label(lesson.getTitle());

        Button startBtn = new Button("Пройти тест");

        startBtn.setOnAction(e -> openTest(lesson));

        card.getChildren().addAll(title, startBtn);
        testsContainer.getChildren().add(card);
    }

    private void openTest(Lesson lesson) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/nickolas/medicalassistanceapp/test-view.fxml")
            );

            Node node = loader.load();

            TestController controller = loader.getController();
            controller.setLesson(lesson);

            controller.setMainController(mainController);

            mainController.setContent(node);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
