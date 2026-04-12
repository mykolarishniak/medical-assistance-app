package com.nickolas.medicalassistanceapp.dao;

import com.nickolas.medicalassistanceapp.db.Database;
import com.nickolas.medicalassistanceapp.model.Answer;
import com.nickolas.medicalassistanceapp.model.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    public List<Question> getByLesson(int lessonId) {
        List<Question> list = new ArrayList<>();

        try (Connection conn = Database.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM questions WHERE lesson_id=?"
            );
            ps.setInt(1, lessonId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Question(
                        rs.getInt("id"),
                        rs.getInt("lesson_id"),
                        rs.getString("question")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public void addQuestion(Question q) {
        try (Connection conn = Database.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO questions (lesson_id, question) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setInt(1, q.getLessonId());
            ps.setString(2, q.getQuestion());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                q.setId(rs.getInt(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteQuestion(int id) {
        try (Connection conn = Database.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM questions WHERE id=?"
            );

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateQuestion(Question q) {
        try (Connection conn = Database.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE questions SET question=?, lesson_id=? WHERE id=?"
            );

            ps.setString(1, q.getQuestion());
            ps.setInt(2, q.getLessonId()); // 🔥 ВАЖЛИВО
            ps.setInt(3, q.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Question> getAllQuestions() {
        List<Question> list = new ArrayList<>();

        try (Connection conn = Database.getConnection()) {

            ResultSet rs = conn.createStatement()
                    .executeQuery("SELECT * FROM questions");

            while (rs.next()) {
                list.add(new Question(
                        rs.getInt("id"),
                        rs.getInt("lesson_id"),
                        rs.getString("question")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
