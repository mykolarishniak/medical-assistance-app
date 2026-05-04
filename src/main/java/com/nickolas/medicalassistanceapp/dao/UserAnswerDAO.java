package com.nickolas.medicalassistanceapp.dao;

import com.nickolas.medicalassistanceapp.db.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserAnswerDAO {
    public void saveAnswer(int userId, int questionId,
                           String selected, String correct) {

        String sql = """
            INSERT INTO user_answers 
            (user_id, question_id, selected_answer, correct_answer, is_correct)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, questionId);
            ps.setString(3, selected);
            ps.setString(4, correct);
            ps.setBoolean(5, selected.equals(correct));

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
