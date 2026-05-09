package com.nickolas.medicalassistanceapp.model;

public class Advice {
private int id;
    private String keyword;
    private String causes;
    private String recommendation;

    public Advice(int id, String keyword, String causes, String recommendation) {
        this.id = id;
        this.keyword = keyword;
        this.causes = causes;
        this.recommendation = recommendation;
    }

    public int getId() {
        return id;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getCauses() {
        return causes;
    }

    public String getRecommendation() {
        return recommendation;
    }
}
