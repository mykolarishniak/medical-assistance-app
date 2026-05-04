package com.nickolas.medicalassistanceapp.model;

public class ProgressDetail {
    private String lessonTitle;
    private boolean lessonCompleted;
    private int correct;
    private int total;

    public ProgressDetail(String lessonTitle, boolean lessonCompleted, int correct, int total) {
        this.lessonTitle = lessonTitle;
        this.lessonCompleted = lessonCompleted;
        this.correct = correct;
        this.total = total;
    }

    public String getLessonTitle() { return lessonTitle; }
    public boolean isLessonCompleted() { return lessonCompleted; }
    public int getCorrect() { return correct; }
    public int getTotal() { return total; }
}
