package com.nickolas.medicalassistanceapp.dao;

import com.nickolas.medicalassistanceapp.db.Database;
import com.nickolas.medicalassistanceapp.model.Answer;
import com.nickolas.medicalassistanceapp.model.AnswerDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AnswerDAO {
    public List<Answer> getByQuestion(int questionId) {
        List<Answer> list = new ArrayList<>();

        try (Connection conn = Database.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM answers WHERE question_id=?"
            );
            ps.setInt(1, questionId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Answer(
                        rs.getInt("id"),
                        rs.getInt("question_id"),
                        rs.getString("answer"),
                        rs.getBoolean("is_correct")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addAnswer(Answer a) {
        try (Connection conn = Database.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO answers (question_id, answer, is_correct) VALUES (?, ?, ?)"
            );

            ps.setInt(1, a.getQuestionId());
            ps.setString(2, a.getAnswer());
            ps.setBoolean(3, a.isCorrect());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateAnswer(Answer a) {
        try (Connection conn = Database.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE answers SET answer=?, is_correct=? WHERE id=?"
            );

            ps.setString(1, a.getAnswer());
            ps.setBoolean(2, a.isCorrect());
            ps.setInt(3, a.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAnswer(int id) {
        try (Connection conn = Database.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM answers WHERE id=?"
            );

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteByQuestion(int questionId) {
        try (Connection conn = Database.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM answers WHERE question_id=?"
            );

            ps.setInt(1, questionId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<AnswerDetail> getUserAnswers(int userId) {

        List<AnswerDetail> list = new ArrayList<>();

        String sql = """
    SELECT q.question, ua.selected_answer, ua.correct_answer, ua.is_correct
    FROM user_answers ua
    JOIN questions q ON ua.question_id = q.id
    WHERE ua.user_id = ?
""";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new AnswerDetail(
                        rs.getString("question"),
                        rs.getString("selected_answer"),
                        rs.getString("correct_answer"),
                        rs.getBoolean("is_correct")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
