package com.nickolas.medicalassistanceapp.dao;

import com.nickolas.medicalassistanceapp.db.Database;
import com.nickolas.medicalassistanceapp.model.Lesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LessonDAO {
    public List<Lesson> getAllLessons() {
        List<Lesson> list = new ArrayList<>();

        try (Connection conn = Database.getConnection()) {

            ResultSet rs = conn.createStatement()
                    .executeQuery("SELECT * FROM lessons");

            while (rs.next()) {
                list.add(new Lesson(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addLesson(Lesson lesson) {
        try (Connection conn = Database.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO lessons(title, content) VALUES (?,?)"
            );

            ps.setString(1, lesson.getTitle());
            ps.setString(2, lesson.getContent());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteLesson(int id) {
        try (Connection conn = Database.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM lessons WHERE id=?"
            );

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateLesson(Lesson lesson) {
        try (Connection conn = Database.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE lessons SET title=?, content=? WHERE id=?"
            );

            ps.setString(1, lesson.getTitle());
            ps.setString(2, lesson.getContent());
            ps.setInt(3, lesson.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
