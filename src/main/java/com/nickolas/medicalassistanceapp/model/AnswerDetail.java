package com.nickolas.medicalassistanceapp.model;

public class AnswerDetail {
    private String questionText;
    private String selected;
    private String correct;
    private boolean isCorrect;

    public AnswerDetail(String questionText, String selected,
                        String correct, boolean isCorrect) {
        this.questionText = questionText;
        this.selected = selected;
        this.correct = correct;
        this.isCorrect = isCorrect;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getSelected() {
        return selected;
    }

    public String getCorrect() {
        return correct;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
