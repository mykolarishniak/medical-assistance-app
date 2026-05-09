package com.nickolas.medicalassistanceapp.controllers;

import com.nickolas.medicalassistanceapp.dao.AdviceDAO;
import com.nickolas.medicalassistanceapp.model.Advice;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class AdminAdviceController {
@FXML
private ListView<Advice> adviceList;

    @FXML
    private TextField keywordField;

    @FXML
    private TextArea causesArea;

    @FXML
    private TextArea recommendationArea;

    private final AdviceDAO dao = new AdviceDAO();
    private List<Advice> adviceData = new ArrayList<>();

    @FXML
    public void initialize() {

        loadAdvice();

        adviceList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                fillForm(newVal);
            }
        });

        adviceList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Advice item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else if (item.getId() == -1) {
                    setText("➕ Новий запис...");
                } else {
                    setText(item.getKeyword());
                }
            }
        });
    }

    private void loadAdvice() {
        adviceData = dao.getAll();

        adviceData.add(new Advice(-1, "", "", ""));

        adviceList.getItems().setAll(adviceData);

        adviceList.getSelectionModel().selectLast();
    }

    private void fillForm(Advice a) {

        if (a.getId() == -1) {
            clearForm();
            return;
        }

        keywordField.setText(a.getKeyword());
        causesArea.setText(a.getCauses());
        recommendationArea.setText(a.getRecommendation());
    }

    private void clearForm() {
        keywordField.clear();
        causesArea.clear();
        recommendationArea.clear();
    }

    @FXML
    private void addAdvice() {

        String keyword = keywordField.getText();
        String causes = causesArea.getText();
        String rec = recommendationArea.getText();

        if (keyword.isEmpty()) {
            showAlert("Введіть keyword");
            return;
        }

        dao.add(keyword, causes, rec);

        loadAdvice();
    }

    @FXML
    private void updateAdvice() {

        Advice selected = adviceList.getSelectionModel().getSelectedItem();

        if (selected == null || selected.getId() == -1) {
            showAlert("Оберіть запис");
            return;
        }

        dao.update(
                selected.getId(),
                keywordField.getText(),
                causesArea.getText(),
                recommendationArea.getText()
        );

        loadAdvice();
    }

    @FXML
    private void deleteAdvice() {

        Advice selected = adviceList.getSelectionModel().getSelectedItem();

        if (selected == null || selected.getId() == -1) {
            showAlert("Оберіть запис");
            return;
        }

        dao.delete(selected.getId());

        loadAdvice();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(msg);
        alert.show();
    }
}
