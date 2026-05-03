package com.nickolas.medicalassistanceapp.model;

public class Progress {
    private String lessonTitle;
    private int score;
    private boolean completed;

    public Progress(String lessonTitle, int score, boolean completed) {
        this.lessonTitle = lessonTitle;
        this.score = score;
        this.completed = completed;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public int getScore() {
        return score;
    }

    public boolean isCompleted() {
        return completed;
    }
}
