package com.nickolas.medicalassistanceapp.model;

public class Lesson {
    private int id;
    private String title;
    private String content;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Lesson(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Lesson(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "ID " + id + ": " + title;
    }
}
