package com.nickolas.medicalassistanceapp.controllers;

import com.nickolas.medicalassistanceapp.dao.AdviceDAO;
import com.nickolas.medicalassistanceapp.model.Advice;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.*;

public class AdviceController {
    @FXML
    private TextField symptomField;

    @FXML
    private TextArea resultArea;

    private final AdviceDAO dao = new AdviceDAO();
    private List<Advice> adviceList;

    private static final List<String> CRITICAL_WORDS = List.of(
            "кров", "кров'ю", "кровю",
            "втрата свідомості", "непритомність",
            "судоми",
            "сильний біль", "біль у грудях"
    );

    private static final List<String> NEGATIVE_WORDS = List.of(
            "болить", "біль", "погано", "нудить",
            "температура", "слабкість", "запаморочення",
            "кашель", "ломить", "втома",
            "не", "проблема", "безсоння",
            "кашляю", "важко", "дихати"
    );

    @FXML
    public void initialize() {
        adviceList = dao.getAll();
    }

    @FXML
    private void handleAdvice() {

        String input = symptomField.getText().toLowerCase().trim();

        if (input.isEmpty()) {
            resultArea.setText("Введіть симптом");
            return;
        }

        for (String word : CRITICAL_WORDS) {
            if (input.contains(word)) {
                resultArea.setText("""
🚨 Небезпечний симптом!

Можливо це серйозна проблема.

👉 Рекомендації:
- негайно зверніться до лікаря
- або викличте швидку допомогу
""");
                return;
            }
        }

        boolean isSymptom = false;

        for (String word : NEGATIVE_WORDS) {
            if (input.contains(word)) {
                isSymptom = true;
                break;
            }
        }

        if (!isSymptom) {
            resultArea.setText("❗ Симптом не розпізнано\n" +
                    "\n" +
                    "Можливо:\n" +
                    "- ви ввели не медичний запит\n" +
                    "- або такого симптому немає в базі\n" +
                    "\n" +
                    "\uD83D\uDC49 Спробуйте описати симптом інакше\n" +
                    "\uD83D\uDC49 або зверніться до лікаря\n" +
                    "\"\"\");");
            return;
        }

        Set<String> words = new HashSet<>(Arrays.asList(input.split("\\s+")));

        Advice bestMatch = null;
        int bestScore = 0;

        for (Advice a : adviceList) {

            String keyword = a.getKeyword().toLowerCase();
            int score = 0;

            for (String word : words) {
                if (input.contains(keyword)) {
                    score++;
                }
            }

            if (score > bestScore) {
                bestScore = score;
                bestMatch = a;
            }
        }

        if (bestMatch != null && bestScore > 0) {

            resultArea.setText(
                    "Можливі причини:\n" +
                            bestMatch.getCauses() +
                            "\n\n👉 Рекомендації:\n" +
                            bestMatch.getRecommendation()
            );

        } else {

            resultArea.setText("""
⚠️ Симптоми не розпізнані точно

Можливо це:
- перевтома
- стрес
- недосипання

👉 Рекомендації:
- відпочити
- пити воду
- звернутись до лікаря
""");
        }
    }
}
