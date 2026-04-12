package com.nickolas.medicalassistanceapp.model;

public class Answer {
    private int id;
    private int questionId;
    private String answer;
    private boolean isCorrect;

    public Answer(int id, int questionId, String answer, boolean isCorrect) {
        this.id = id;
        this.questionId = questionId;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public String getAnswer() { return answer; }
    public boolean isCorrect() { return isCorrect; }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public int getId() {
        return id;
    }

    public int getQuestionId() {
        return questionId;
    }
}
