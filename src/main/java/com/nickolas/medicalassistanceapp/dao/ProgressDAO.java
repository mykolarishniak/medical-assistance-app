package com.nickolas.medicalassistanceapp.dao;

import com.nickolas.medicalassistanceapp.db.Database;
import com.nickolas.medicalassistanceapp.model.ProgressDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProgressDAO {
public void markLessonCompleted(int userId, int lessonId) {
    if (userId <= 0) return;

    String sql = """
            INSERT INTO user_progress (user_id, lesson_id, lesson_completed)
            VALUES (?, ?, 1)
            ON DUPLICATE KEY UPDATE lesson_completed = 1
        """;

    try (Connection conn = Database.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, userId);
        ps.setInt(2, lessonId);
        ps.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public void saveTestResult(int userId, int lessonId, int correct, int total) {
        if (userId <= 0) return;

        String sql = """
            INSERT INTO user_progress 
            (user_id, lesson_id, test_completed, correct_answers, total_questions)
            VALUES (?, ?, 1, ?, ?)
            ON DUPLICATE KEY UPDATE
                test_completed = 1,
                correct_answers = ?,
                total_questions = ?
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, lessonId);
            ps.setInt(3, correct);
            ps.setInt(4, total);
            ps.setInt(5, correct);
            ps.setInt(6, total);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCompletedLessons(int userId) {
        String sql = "SELECT COUNT(*) FROM user_progress WHERE user_id=? AND lesson_completed=1";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getCompletedTests(int userId) {
        String sql = "SELECT COUNT(*) FROM user_progress WHERE user_id=? AND test_completed=1";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalCorrect(int userId) {
        String sql = "SELECT SUM(correct_answers) FROM user_progress WHERE user_id=?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalQuestionsAnswered(int userId) {
        String sql = "SELECT SUM(total_questions) FROM user_progress WHERE user_id=?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalLessons() {
        String sql = "SELECT COUNT(*) FROM lessons";

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void markLessonRead(int userId, int lessonId) {
        if (userId <= 0) return;

        String sql = """
            INSERT INTO user_progress (user_id, lesson_id, lesson_completed)
            VALUES (?, ?, 1)
            ON DUPLICATE KEY UPDATE lesson_completed = 1
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, lessonId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTotalTests() {
        String sql = "SELECT COUNT(DISTINCT lesson_id) FROM questions";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void resetProgress(int userId) {
        String sql = "DELETE FROM user_progress WHERE user_id=?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<ProgressDetail> getProgressDetails(int userId) {
        List<ProgressDetail> list = new ArrayList<>();

        String sql = """
        SELECT l.title, up.lesson_completed, up.correct_answers, up.total_questions
        FROM lessons l
        LEFT JOIN user_progress up 
            ON l.id = up.lesson_id AND up.user_id = ?
    """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new ProgressDetail(
                        rs.getString("title"),
                        rs.getBoolean("lesson_completed"),
                        rs.getInt("correct_answers"),
                        rs.getInt("total_questions")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
