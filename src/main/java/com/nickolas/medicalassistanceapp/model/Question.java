package com.nickolas.medicalassistanceapp.model;

public class Question {
    private int id;
    private int lessonId;
    private String question;

    public Question(int id, int lessonId, String question) {
        this.id = id;
        this.lessonId = lessonId;
        this.question = question;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() { return id; }
    public int getLessonId() { return lessonId; }
    public String getQuestion() { return question; }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    @Override
    public String toString() {
        return "ID " + id + ": " + question;
    }
}
