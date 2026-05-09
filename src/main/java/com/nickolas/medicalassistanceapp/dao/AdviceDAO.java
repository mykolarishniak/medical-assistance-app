package com.nickolas.medicalassistanceapp.dao;

import com.nickolas.medicalassistanceapp.db.Database;
import com.nickolas.medicalassistanceapp.model.Advice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdviceDAO {
public List<Advice> getAll() {
    List<Advice> list = new ArrayList<>();

    String sql = "SELECT * FROM advice";

    try (Connection conn = Database.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            list.add(new Advice(
                    rs.getInt("id"),
                    rs.getString("keyword"),
                    rs.getString("causes"),
                    rs.getString("recommendation")
            ));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

    public void add(String keyword, String causes, String recommendation) {
        String sql = "INSERT INTO advice(keyword, causes, recommendation) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, keyword);
            ps.setString(2, causes);
            ps.setString(3, recommendation);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(int id, String keyword, String causes, String recommendation) {
        String sql = "UPDATE advice SET keyword=?, causes=?, recommendation=? WHERE id=?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, keyword);
            ps.setString(2, causes);
            ps.setString(3, recommendation);
            ps.setInt(4, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM advice WHERE id=?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
