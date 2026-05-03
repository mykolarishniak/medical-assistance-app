package com.nickolas.medicalassistanceapp.service;

import com.nickolas.medicalassistanceapp.dao.ProgressDAO;

public class ProgressService {
    private ProgressDAO dao = new ProgressDAO();

    public double getProgress(int userId) {

        int completedLessons = dao.getCompletedLessons(userId);
        int completedTests = dao.getCompletedTests(userId);
        int totalLessons = dao.getTotalLessons();

        int correct = dao.getTotalCorrect(userId);
        int totalAnswers = dao.getTotalQuestionsAnswered(userId);

        if (totalLessons == 0) return 0;

        double lessonPart = (double) completedLessons / totalLessons;
        double testPart = (double) completedTests / totalLessons;

        double knowledgePart = totalAnswers == 0 ? 0 : (double) correct / totalAnswers;

        return (lessonPart * 0.3) + (testPart * 0.3) + (knowledgePart * 0.4);
    }
}
