package com.nickolas.medicalassistanceapp.model;

public class Progress {
    private String lessonTitle;
    private int score;

    public Progress(String lessonTitle, int score) {
        this.lessonTitle = lessonTitle;
        this.score = score;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public int getScore() {
        return score;
    }
}
